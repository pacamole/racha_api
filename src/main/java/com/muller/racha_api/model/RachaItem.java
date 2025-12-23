package com.muller.racha_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
@Table(name = "racha")
@AllArgsConstructor
@NoArgsConstructor
public class RachaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "representative_id", nullable = false)
    private User representative;

    @Column(nullable = false, length = 30)
    private String title;
    @Column(length = 300)
    private String description;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    private BigDecimal currentlyPaid = BigDecimal.ZERO;

    private LocalDateTime dueDate;

    @OneToMany(mappedBy = "racha", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<RachaParticipant> participants;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false, updatable = true)
    private LocalDateTime updatedAt;

    public BigDecimal calculateTotalPaidAmount() {
        return this.participants.stream()
                .map(RachaParticipant::getValuePaid)
                .reduce(BigDecimal.ZERO,
                        BigDecimal::add);
    }
}
