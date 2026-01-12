package com.muller.racha_api.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muller.racha_api.controller.docs.PaymentControllerDocs;
import com.muller.racha_api.dto.PaymentRequestDTO;
import com.muller.racha_api.model.Payment;
import com.muller.racha_api.model.User;
import com.muller.racha_api.service.PaymentService;
import com.muller.racha_api.service.R2StorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/racha")
@RequiredArgsConstructor
public class PaymentController implements PaymentControllerDocs {
    private final PaymentService paymentService;
    private final R2StorageService storageService;
    private final ObjectMapper objectMapper;

    @PostMapping(value = "/{rachaId}/payment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Payment create(
            @AuthenticationPrincipal User user,
            @PathVariable String rachaId,
            @RequestPart("file") MultipartFile file,
            @RequestParam("data") String dataJson) throws Exception {

        PaymentRequestDTO dto = objectMapper.readValue(dataJson, PaymentRequestDTO.class);

        String imageUrl = null;

        if (file != null && !file.isEmpty()) {
            imageUrl = storageService.upload(file);
        }

        return paymentService.create(user.getId(), UUID.fromString(rachaId), dto, imageUrl);
    }

    @GetMapping("/{rachaId}/payment")
    public Page<Payment> findAllByRacha(@AuthenticationPrincipal User user, @PathVariable String rachaId) {
        return paymentService.findAllByRacha(user.getId(), UUID.fromString(rachaId), null);
    }

    @PutMapping("/{rachaId}/payment/{paymentId}")
    public Payment update(
            @AuthenticationPrincipal User user,
            @PathVariable String rachaId,
            @PathVariable String paymentId,
            @RequestPart("file") MultipartFile file,
            @RequestParam("data") String dataJson) throws Exception {

        PaymentRequestDTO dto = objectMapper.readValue(dataJson, PaymentRequestDTO.class);

        String imageUrl = null;

        if (file != null && !file.isEmpty()) {
            imageUrl = storageService.upload(file);
        }
        return paymentService.update(user.getId(), UUID.fromString(rachaId), UUID.fromString(paymentId), dto, imageUrl);
    }

    @DeleteMapping("/{rachaId}/payment/{paymentId}")
    public void delete(@AuthenticationPrincipal User user, @PathVariable String rachaId,
            @PathVariable String paymentId) {
        paymentService.delete(user.getId(), UUID.fromString(rachaId), UUID.fromString(paymentId));
    }

}
