package com.muller.racha_api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muller.racha_api.model.PixKey;

@Repository
public interface PixKeyRepository extends JpaRepository<PixKey, UUID> {

    public List<PixKey> findAllByUserId(UUID userId);

    public PixKey findByKeyAndUserId(String key, UUID userId);

    public boolean existsByKeyAndUserId(String key, UUID userId);

}
