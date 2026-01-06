package com.muller.racha_api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.muller.racha_api.dto.PixKeyRequestDTO;
import com.muller.racha_api.model.PixKey;
import com.muller.racha_api.model.User;
import com.muller.racha_api.repository.PixKeyRepository;
import com.muller.racha_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PixKeyService {
    private final PixKeyRepository pixRepository;
    private final UserRepository userRepository;

    public PixKey create(UUID userId, PixKeyRequestDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new IllegalArgumentException("Usuário não encontrado para salvar o pix");
        });

        PixKey key = PixKey.toEntity(dto, user);

        if (pixRepository.existsByKeyAndUserId(key.getKey(), userId)) {
            throw new IllegalArgumentException("Usuário já registrou essa chave");
        }

        return pixRepository.save(key);
    }

    public List<PixKey> findAllByUser(UUID userId) {
        return pixRepository.findAllByUserId(userId);
    }

    public PixKey update(UUID userId, UUID pixId, PixKeyRequestDTO dto) {
        PixKey pix = pixRepository.findById(pixId).orElseThrow(() -> {
            throw new IllegalArgumentException("Chave pix não encontrada");
        });

        if (!pix.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Chave pix não pertence ao usuário");
        }

        if (!dto.getKey().isBlank()) {
            pix.setKey(dto.getKey());
        }
        if (dto.getType() != null && !dto.getType().equals(pix.getType())) {
            pix.setType(dto.getType());
        }

        return pixRepository.save(pix);
    }

    public void delete(UUID userId, UUID pixId) {
        PixKey pix = pixRepository.findById(pixId).orElseThrow(() -> {
            throw new IllegalArgumentException("Chave pix não encontrada");
        });

        if (!pix.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Chave pix não pertence ao usuário");
        }

        pixRepository.delete(pix);
    }
}
