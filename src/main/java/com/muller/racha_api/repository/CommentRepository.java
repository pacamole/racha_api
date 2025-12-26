package com.muller.racha_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muller.racha_api.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
