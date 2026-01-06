package com.muller.racha_api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.muller.racha_api.dto.PaymentRequestDTO;
import com.muller.racha_api.model.Payment;
import com.muller.racha_api.model.RachaItem;
import com.muller.racha_api.model.RachaParticipant;
import com.muller.racha_api.model.User;
import com.muller.racha_api.repository.PaymentRepository;
import com.muller.racha_api.repository.RachaItemRepository;
import com.muller.racha_api.repository.RachaParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RachaItemRepository rachaRepository;
    private final RachaParticipantRepository participantRepository;

    @Transactional
    public Payment create(UUID userId, UUID rachaId, PaymentRequestDTO dto) {
        RachaParticipant participant = participantRepository.findByUserIdAndRachaId(userId, rachaId).orElseThrow(() -> {
            throw new IllegalArgumentException("Não é um participante desta racha ou racha não existe");
        });

        Payment payment = Payment.toEntity(dto, participant);
        Payment paymentSaved = paymentRepository.save(payment);

        BigDecimal participantTotalPaid = participant.getValuePaid().add(payment.getPaymentValue());
        participant.setValuePaid(participantTotalPaid);
        participant.setPaidAt(LocalDateTime.now());
        participantRepository.save(participant);

        RachaItem racha = participant.getRacha();
        BigDecimal rachaCurrentlyPaid = racha.getCurrentlyPaid().add(payment.getPaymentValue());
        racha.setCurrentlyPaid(rachaCurrentlyPaid);
        rachaRepository.save(racha);

        return paymentSaved;
    }

    public Page<Payment> findAllByRacha(UUID userId, UUID rachaId, Pageable pageable) {
        RachaItem racha = rachaRepository.findById(rachaId).orElseThrow(() -> {
            throw new IllegalArgumentException("Racha não encontrada");
        });

        if (!racha.getRepresentative().getId().equals(userId)) {
            throw new IllegalArgumentException("Usuário não é o representante da racha");
        }

        return paymentRepository.findByParticipantRachaId(pageable, rachaId);
    }

    @Transactional
    public Payment update(UUID userId, UUID rachaId, UUID paymentId, PaymentRequestDTO dto) {

        // Validações
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> {
            throw new IllegalArgumentException("Pagamento indicado não existe");
        });

        RachaParticipant participant = payment.getParticipant();
        RachaItem racha = participant.getRacha();
        if (!racha.getId().equals(rachaId)) {
            throw new IllegalArgumentException("Usuário não faz parte do item de racha");
        }

        User participantUser = participant.getUser();
        if (!participantUser.getId().equals(userId)) {
            throw new IllegalArgumentException("Não é o usuario dono do pagamento");
        }

        // Atualizações simples
        if (!dto.getImageUrl().isBlank()) {
            payment.setImageUrl(dto.getImageUrl());
        }
        if (!dto.getMessage().isBlank()) {
            payment.setMessage(dto.getMessage());
        }

        // Atualização de Valores
        BigDecimal oldValue = payment.getPaymentValue();
        BigDecimal newValue = dto.getPaymentValue();

        if (newValue != null && !oldValue.equals(newValue)) {
            payment.setPaymentValue(newValue);

            payment.setPaidAt(LocalDateTime.now());
            Payment newPayment = paymentRepository.save(payment);

            // Valor pago do participante
            RachaParticipant updatedParticipant = newPayment.getParticipant();
            BigDecimal totalPaid = updatedParticipant.getPayments()
                    .stream()
                    .map(Payment::getPaymentValue)
                    .reduce(BigDecimal.ZERO,
                            BigDecimal::add);

            updatedParticipant.setValuePaid(totalPaid);
            participantRepository.save(updatedParticipant);

            // Valor pago da racha
            RachaItem updatedRacha = updatedParticipant.getRacha();
            BigDecimal difference = newValue.subtract(oldValue);
            BigDecimal newRachaTotal = updatedRacha.getCurrentlyPaid().add(difference);

            updatedRacha.setCurrentlyPaid(newRachaTotal);
            rachaRepository.save(updatedRacha);

            return newPayment;
        } else {
            return paymentRepository.save(payment);
        }
    }

    @Transactional
    public void delete(UUID userId, UUID rachaId, UUID paymentId) {
        // Validações
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> {
            throw new IllegalArgumentException("Pagamento indicado não existe");
        });
        RachaParticipant participant = payment.getParticipant();
        RachaItem racha = participant.getRacha();
        if (!racha.getId().equals(rachaId)) {
            throw new IllegalArgumentException("Usuário não faz parte do item de racha");
        }
        User participantUser = participant.getUser();
        if (!participantUser.getId().equals(userId)) {
            throw new IllegalArgumentException("Não é o usuario dono do pagamento");
        }
        // Valor pago do participante
        BigDecimal paymentValue = payment.getPaymentValue();
        BigDecimal participantTotalPaid = participant.getValuePaid().subtract(paymentValue);
        participant.setValuePaid(participantTotalPaid);
        participantRepository.save(participant);

        // Valor pago da racha
        RachaItem rachaItem = participant.getRacha();
        BigDecimal rachaCurrentlyPaid = rachaItem.getCurrentlyPaid().subtract(paymentValue);
        rachaItem.setCurrentlyPaid(rachaCurrentlyPaid);
        rachaRepository.save(rachaItem);

        // Deleção do pagamento
        paymentRepository.delete(payment);
    }
}
