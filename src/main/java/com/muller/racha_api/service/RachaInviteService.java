package com.muller.racha_api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.muller.racha_api.model.RachaInvite;
import com.muller.racha_api.model.RachaItem;
import com.muller.racha_api.model.RachaParticipant;
import com.muller.racha_api.model.User;
import com.muller.racha_api.repository.RachaInviteRepository;
import com.muller.racha_api.repository.RachaItemRepository;
import com.muller.racha_api.repository.RachaParticipantRepository;
import com.muller.racha_api.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RachaInviteService {
    private final RachaInviteRepository inviteRepository;
    private final RachaItemRepository rachaRepository;
    private final UserRepository userRepository;
    private final RachaParticipantRepository participantRepository;

    @Transactional
    public String create(UUID userId, UUID rachaId) {
        RachaItem racha = rachaRepository.findById(rachaId).orElseThrow(() -> {
            throw new IllegalArgumentException("Racha não encontrada");
        });

        if (!racha.getRepresentative().getId().equals(userId)) {
            throw new IllegalArgumentException("Apenas o representante pode criar convites");
        }

        if (racha.getInvitation() != null && !racha.getInvitation().isExpired()) {
            return "https://meuapp.com/join/" + racha.getInvitation().getId();
        }

        RachaInvite invite = new RachaInvite();
        invite.setRacha(racha);
        invite.setExpiresAt(racha.getPaymentDate());

        RachaInvite savedInvite = inviteRepository.save(invite);

        racha.setInvitation(invite);
        rachaRepository.save(racha);

        return "https://meuapp.com/join/" + savedInvite.getId();
    }

    @Transactional
    public RachaItem joinRacha(UUID userId, UUID inviteId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new IllegalArgumentException("Usuário não encontrado");
        });

        RachaInvite invite = inviteRepository.findById(inviteId).orElseThrow(() -> {
            throw new IllegalArgumentException("Convite inválido");
        });

        if (invite.isExpired()) {
            throw new IllegalArgumentException("O convite expirou, não pode mais entrar na racha");
        }

        RachaItem racha = rachaRepository.findById(invite.getRacha().getId()).orElseThrow(() -> {
            throw new IllegalArgumentException("Racha não encontrada");
        });

        boolean alreadyIn = participantRepository.existsByUserIdAndRachaId(userId, racha.getId());
        if (alreadyIn) {
            throw new IllegalArgumentException("Usuário já participa desta racha");
        }

        RachaParticipant participant = new RachaParticipant();
        participant.setUser(user);
        participant.setRacha(racha);
        participant.setPayments(new ArrayList<>());
        participant.setComments(new ArrayList<>());
        participant.setValuePaid(BigDecimal.ZERO);

        racha.getParticipants().add(participant);

        BigDecimal valueToPay = RachaService.calculateValueToPay(racha.getTotalPrice(),
                racha.getParticipants().size());

        racha.getParticipants().forEach(part -> part.setValueToPay(valueToPay));

        // cascade faz salvar os participantes também
        return rachaRepository.save(racha);
    }
}
