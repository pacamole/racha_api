package com.muller.racha_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muller.racha_api.dto.PaymentRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    @JsonIgnore
    private RachaParticipant participant;

    @Column(nullable = false)
    private BigDecimal paymentValue;
    private String imageUrl;
    private String message;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime paidAt;

    public static Payment toEntity(PaymentRequestDTO dto, RachaParticipant participant) {
        Payment payment = new Payment();
        payment.setPaymentValue(dto.getPaymentValue());
        payment.setImageUrl(dto.getImageUrl());
        payment.setMessage(dto.getMessage());
        payment.setParticipant(participant);
        return payment;
    }

}
