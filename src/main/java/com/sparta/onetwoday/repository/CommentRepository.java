package com.sparta.onetwoday.repository;

import com.sparta.onetwoday.dto.CommentResponseDto;
import com.sparta.onetwoday.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentResponseDto> findByIdOrderByCreatedAtDesc(Long id);
}