package com.muller.racha_api.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muller.racha_api.dto.PaymentRequestDTO;
import com.muller.racha_api.model.Payment;
import com.muller.racha_api.model.User;
import com.muller.racha_api.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{rachaId}")
    public Payment sendPayment(@AuthenticationPrincipal User user, @PathVariable String rachaId,
            @RequestBody @Valid PaymentRequestDTO dto) {
        return paymentService.sendPayment(user.getId(), UUID.fromString(rachaId), dto);
    }

    @GetMapping("/{rachaId}")
    public Page<Payment> listPaymentsByRacha(@AuthenticationPrincipal User user, @PathVariable String rachaId) {
        return paymentService.listPaymentsByRacha(user.getId(), UUID.fromString(rachaId), null);
    }

    @PutMapping("/{rachaId}/{paymentId}")
    public Payment updatePaymentStatus(@AuthenticationPrincipal User user, @PathVariable String rachaId,
            @PathVariable String paymentId, @RequestBody @Valid PaymentRequestDTO dto) {
        return paymentService.editPayment(user.getId(), UUID.fromString(rachaId), UUID.fromString(paymentId), dto);
    }

    @DeleteMapping("/{rachaId}/{paymentId}")
    public void deletePayment(@AuthenticationPrincipal User user, @PathVariable String rachaId,
            @PathVariable String paymentId) {
        paymentService.deletePayment(user.getId(), UUID.fromString(rachaId), UUID.fromString(paymentId));
    }

}
