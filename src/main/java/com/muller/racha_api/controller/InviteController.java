package com.muller.racha_api.controller;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muller.racha_api.controller.docs.InviteControllerDocs;
import com.muller.racha_api.model.RachaItem;
import com.muller.racha_api.model.User;
import com.muller.racha_api.service.RachaInviteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/invite")
@RequiredArgsConstructor
public class InviteController implements InviteControllerDocs {
    private final RachaInviteService inviteService;

    @PostMapping("/racha/{rachaId}")
    public String create(@AuthenticationPrincipal User user, @PathVariable String rachaId) {
        return inviteService.create(user.getId(), UUID.fromString(rachaId));
    }

    @PostMapping("/{inviteId}/join")
    public RachaItem joinRacha(@AuthenticationPrincipal User user, @PathVariable String inviteId) {
        return inviteService.joinRacha(user.getId(), UUID.fromString(inviteId));
    }
}
