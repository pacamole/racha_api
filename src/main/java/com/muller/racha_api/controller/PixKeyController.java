package com.muller.racha_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muller.racha_api.dto.PixKeyRequestDTO;
import com.muller.racha_api.model.PixKey;
import com.muller.racha_api.model.User;
import com.muller.racha_api.service.PixKeyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/me/pixkey")
@RequiredArgsConstructor
public class PixKeyController {
    private final PixKeyService pixKeyService;

    @PostMapping
    public PixKey create(@AuthenticationPrincipal User user, @RequestBody @Valid PixKeyRequestDTO dto) {
        return pixKeyService.create(user.getId(), dto);
    }

    @GetMapping
    public List<PixKey> findAllByUser(@AuthenticationPrincipal User user) {
        return pixKeyService.findAllByUser(user.getId());
    }

    @PutMapping("/{pixId}")
    public PixKey update(@AuthenticationPrincipal User user, @PathVariable String pixId,
            @RequestBody @Valid PixKeyRequestDTO dto) {
        return pixKeyService.update(user.getId(), UUID.fromString(pixId), dto);
    }

    @DeleteMapping("/{pixId}")
    public void delete(@AuthenticationPrincipal User user, @PathVariable String pixId) {
        pixKeyService.delete(user.getId(), UUID.fromString(pixId));
    }

}
