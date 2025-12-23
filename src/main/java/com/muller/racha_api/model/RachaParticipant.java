package com.muller.racha_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "racha_participant")
@AllArgsConstructor
@NoArgsConstructor
public class RachaParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "racha_id", nullable = false)
    private RachaItem racha;

    @Column(nullable = false)
    private BigDecimal valuePaid = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal valueToPay;

    // Pagamentos ligados ao Participante
    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    // Comentários ligados ao Participante (Sua correção final!)
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime paidAt;

    public boolean isFullyPaid() {
        // valuePaid > valueToPay = true
        // valuePaid = valueToPay = true
        // valuePaid < valueToPay = false
        return this.valuePaid.compareTo(this.valueToPay) >= 0;
    }

}
