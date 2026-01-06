package com.muller.racha_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.muller.racha_api.dto.CommentRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String content;

    @ManyToOne
    @JoinColumn(name = "racha_participant_id", nullable = false)
    @JsonIgnore
    private RachaParticipant author;

    public static Comment toEntity(CommentRequestDTO dto) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());

        return comment;
    }

}
