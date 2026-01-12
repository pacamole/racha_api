package com.muller.racha_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muller.racha_api.controller.docs.CommentControllerDocs;
import com.muller.racha_api.dto.CommentRequestDTO;
import com.muller.racha_api.model.Comment;
import com.muller.racha_api.model.User;
import com.muller.racha_api.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/racha")
@RequiredArgsConstructor
public class CommentController implements CommentControllerDocs {
    private final CommentService commentService;

    @PostMapping("/{rachaId}/comment")
    public Comment create(@AuthenticationPrincipal User user, @PathVariable String rachaId,
            @RequestBody @Valid CommentRequestDTO dto) {
        return commentService.create(user.getId(), UUID.fromString(rachaId), dto);
    }

    @GetMapping("/{rachaId}/comment")
    public List<Comment> findByRacha(@AuthenticationPrincipal User user, @PathVariable String rachaId) {
        return commentService.findAllByRachaId(user.getId(), UUID.fromString(rachaId));
    }

    @DeleteMapping("/comment/{commentId}")
    public void delete(@AuthenticationPrincipal User user, @PathVariable String commentId) {
        commentService.delete(user.getId(), Long.decode(commentId));
    }
}
