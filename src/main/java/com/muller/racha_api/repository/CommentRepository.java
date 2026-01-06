package com.muller.racha_api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.muller.racha_api.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.author.racha.id = :rachaId ORDER BY c.id DESC")
    List<Comment> findByRachaId(UUID rachaId);
}