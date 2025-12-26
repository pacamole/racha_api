package com.muller.racha_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muller.racha_api.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}
