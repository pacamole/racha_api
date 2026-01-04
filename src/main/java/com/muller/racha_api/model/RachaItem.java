package com.muller.racha_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.muller.racha_api.dto.RachaItemRequestDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(mappedBy = "racha", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RachaParticipant> participants = new ArrayList<RachaParticipant>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(updatable = true)
    private LocalDateTime updatedAt;

    public static final RachaItem toEntity(RachaItemRequestDTO dto) {
        RachaItem racha = new RachaItem();
        racha.setId(null);
        racha.setRepresentative(null);
        racha.setTitle(dto.getTitle());
        racha.setDescription(dto.getDescription());
        racha.setTotalPrice(dto.getTotalPrice());
        racha.setDueDate(dto.getDueDate());
        return racha;
    }

    public static final RachaItemRequestDTO toDTO(RachaItem racha) {
        RachaItemRequestDTO dto = new RachaItemRequestDTO();
        dto.setTitle(racha.getTitle());
        dto.setDescription(racha.getDescription());
        dto.setTotalPrice(racha.getTotalPrice());
        dto.setDueDate(racha.getDueDate());
        return dto;
    }

    public BigDecimal calculateTotalPaidAmount() {
        return this.participants.stream()
                .map(RachaParticipant::getValuePaid)
                .reduce(BigDecimal.ZERO,
                        BigDecimal::add);
    }
}
