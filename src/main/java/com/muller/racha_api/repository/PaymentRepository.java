package com.muller.racha_api.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muller.racha_api.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    public Page<Payment> findByParticipantRachaId(Pageable pageable, UUID rachaId);

}
