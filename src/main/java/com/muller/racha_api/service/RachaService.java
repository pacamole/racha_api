package com.muller.racha_api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muller.racha_api.dto.RachaItemRequestDTO;
import com.muller.racha_api.model.RachaItem;
import com.muller.racha_api.model.RachaParticipant;
import com.muller.racha_api.model.User;
import com.muller.racha_api.repository.RachaItemRepository;
import com.muller.racha_api.repository.UserRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RachaService {
    private final UserRepository userRepository;
    private final RachaItemRepository rachaRepository;

    protected static BigDecimal calculateValueToPay(BigDecimal totalPrice, Integer qttParticipants) {
        BigDecimal valueToPay = totalPrice.divide(
                BigDecimal.valueOf(qttParticipants),
                2, // Escala (casas decimais)
                RoundingMode.HALF_EVEN // Modo de arredondamento
        );
        return valueToPay;

    }

    public RachaItem create(UUID userId, RachaItemRequestDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new IllegalArgumentException("Usuário representante não encontrado");
        });

        RachaItem racha = RachaItem.toEntity(dto);
        racha.setRepresentative(user);
        racha.setPaymentDate(dto.getPaymentDate());

        List<RachaParticipant> participants = new ArrayList<RachaParticipant>();

        if (dto.isRepresentative_participate()) {
            RachaParticipant participant = new RachaParticipant();
            participant.setUser(user);
            participant.setRacha(racha);

            participant.setValuePaid(BigDecimal.ZERO);
            participant.setPaidAt(null);

            participants.add(participant);

            racha.setParticipants(new ArrayList<RachaParticipant>(participants));
            BigDecimal valueToPay = calculateValueToPay(racha.getTotalPrice(), racha.getParticipants().size());
            participant.setValueToPay(valueToPay);

        } else {

            racha.setParticipants(participants);
        }

        return rachaRepository.save(racha);
    }

    public Page<RachaItem> findAllByUser(@NonNull Pageable pageable, UUID userId) {
        return rachaRepository.findAllByRepresentativeId(pageable, userId);
    }

    public RachaItem update(UUID userId, UUID rachaId, RachaItemRequestDTO dto) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuário representante não encontrado");
        }

        RachaItem rachaItem = rachaRepository.findById(rachaId).orElseThrow(() -> {
            throw new IllegalArgumentException("Racha não encontrada");
        });

        if (!rachaItem.getRepresentative().getId().equals(userId)) {
            throw new IllegalArgumentException("Usuário não é o representante da racha");
        }

        if (!dto.getTitle().isBlank()) {
            rachaItem.setTitle(dto.getTitle());
        }
        if (!dto.getDescription().isBlank()) {
            rachaItem.setDescription(dto.getDescription());
        }
        if (!dto.getTotalPrice().equals(rachaItem.getTotalPrice())) {
            rachaItem.setTotalPrice(dto.getTotalPrice());
        }
        if (!dto.getPaymentDate().equals(rachaItem.getPaymentDate())) {
            rachaItem.setPaymentDate(dto.getPaymentDate());
        }

        return rachaRepository.save(rachaItem);
    }

    public void delete(UUID userId, UUID rachaId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        RachaItem rachaItem = rachaRepository.findById(rachaId).orElseThrow(() -> {
            throw new IllegalArgumentException("Racha não encontrada");
        });

        if (!rachaItem.getRepresentative().getId().equals(userId)) {
            throw new IllegalArgumentException("Usuário não é o representante da racha");
        }

        rachaRepository.deleteById(rachaId);
    }
}
