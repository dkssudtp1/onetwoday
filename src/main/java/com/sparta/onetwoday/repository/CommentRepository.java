package com.sparta.onetwoday.repository;


import com.sparta.onetwoday.entity.Comment;
import com.sparta.onetwoday.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findByIdOrderByCreatedAtDesc(Long id);
    List<Comment> findByTravelIdAndIsDeletedOrderByCreatedAtDesc(Long id, Boolean bool);

//    Long findByTravelId(Long id);

    Comment findByIdAndIsDeleted(Long travelId, Boolean bool);
}