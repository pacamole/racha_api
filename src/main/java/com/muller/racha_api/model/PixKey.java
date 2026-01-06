package com.muller.racha_api.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muller.racha_api.dto.PixKeyRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PixKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "key_value", nullable = false, length = 256)
    private String key;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PixType type;

    public static PixKey toEntity(PixKeyRequestDTO dto, User user) {
        PixKey pixKey = new PixKey();
        pixKey.setUser(user);
        pixKey.setKey(dto.getKey());
        pixKey.setType(dto.getType());
        return pixKey;
    }
}