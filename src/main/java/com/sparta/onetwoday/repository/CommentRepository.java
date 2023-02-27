package com.sparta.onetwoday.repository;


import com.sparta.onetwoday.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findByIdOrderByCreatedAtDesc(Long id);
    List<Comment> findByTravelIdOrderByCreatedAtDesc(Long id);

    Long findByTravelId(Long id);
}