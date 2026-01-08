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
@RequestMapping("/racha")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{rachaId}/payment")
    public Payment create(@AuthenticationPrincipal User user, @PathVariable String rachaId,
            @RequestBody @Valid PaymentRequestDTO dto) {
        return paymentService.create(user.getId(), UUID.fromString(rachaId), dto);
    }

    @GetMapping("/{rachaId}/payment")
    public Page<Payment> findAllByRacha(@AuthenticationPrincipal User user, @PathVariable String rachaId) {
        return paymentService.findAllByRacha(user.getId(), UUID.fromString(rachaId), null);
    }

    @PutMapping("/{rachaId}/payment/{paymentId}")
    public Payment update(@AuthenticationPrincipal User user, @PathVariable String rachaId,
            @PathVariable String paymentId, @RequestBody @Valid PaymentRequestDTO dto) {
        return paymentService.update(user.getId(), UUID.fromString(rachaId), UUID.fromString(paymentId), dto);
    }

    @DeleteMapping("/{rachaId}/payment/{paymentId}")
    public void delete(@AuthenticationPrincipal User user, @PathVariable String rachaId,
            @PathVariable String paymentId) {
        paymentService.delete(user.getId(), UUID.fromString(rachaId), UUID.fromString(paymentId));
    }

}
