package com.muller.racha_api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.muller.racha_api.dto.CommentRequestDTO;
import com.muller.racha_api.model.Comment;
import com.muller.racha_api.model.RachaParticipant;
import com.muller.racha_api.model.User;
import com.muller.racha_api.repository.CommentRepository;
import com.muller.racha_api.repository.RachaParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final RachaParticipantRepository participantRepository;

    public Comment create(UUID userId, UUID rachaId, CommentRequestDTO dto) {
        RachaParticipant participant = participantRepository.findByUserIdAndRachaId(userId, rachaId).orElseThrow(() -> {
            throw new IllegalArgumentException("Não é um participante desta racha ou racha não existe");
        });

        Comment comment = Comment.toEntity(dto);
        comment.setAuthor(participant);

        return commentRepository.save(comment);
    }

    public List<Comment> findAllByRachaId(UUID userId, UUID rachaId) {
        if (!participantRepository.existsByUserIdAndRachaId(userId, rachaId)) {
            throw new IllegalArgumentException("Não é um participante desta racha ou racha não existe");
        }

        return commentRepository.findByRachaId(rachaId);
    }

    public void delete(UUID userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new IllegalArgumentException("Comentário não encontrado");
        });

        User user = comment.getAuthor().getUser();
        if (!user.getId().equals(userId)) {
            throw new IllegalArgumentException("Você não tem permissão para deletar este comentário");
        }

        commentRepository.delete(comment);
    }
}