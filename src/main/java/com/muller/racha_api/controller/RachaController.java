package com.muller.racha_api.controller;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muller.racha_api.controller.docs.RachaControllerDocs;
import com.muller.racha_api.dto.RachaItemRequestDTO;
import com.muller.racha_api.model.RachaItem;
import com.muller.racha_api.model.User;
import com.muller.racha_api.service.RachaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/racha")
@RequiredArgsConstructor
public class RachaController implements RachaControllerDocs {
    private final RachaService rachaService;

    @PostMapping
    public RachaItem create(@RequestBody @Valid RachaItemRequestDTO dto, @AuthenticationPrincipal User user) {
        return rachaService.create(user.getId(), dto);
    }

    @GetMapping
    public Page<RachaItem> findAllByUser(
            @ParameterObject @PageableDefault(size = 10, page = 0, sort = "createdAt") Pageable pageable,
            @AuthenticationPrincipal User user) {
        return rachaService.findAllByUser(pageable, user.getId());
    }

    @PutMapping("/{rachaId}")
    public RachaItem update(@PathVariable String rachaId, @RequestBody @Valid RachaItemRequestDTO dto,
            @AuthenticationPrincipal User user) {
        return rachaService.update(user.getId(), UUID.fromString(rachaId), dto);
    }

    @DeleteMapping("/{rachaId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String rachaId, @AuthenticationPrincipal User user) {
        rachaService.delete(user.getId(), UUID.fromString(rachaId));

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
