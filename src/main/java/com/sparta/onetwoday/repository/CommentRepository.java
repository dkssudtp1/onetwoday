package com.sparta.onetwoday.repository;


import com.sparta.onetwoday.entity.Comment;
import com.sparta.onetwoday.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    List<Comment> findByIdOrderByCreatedAtDesc(Long id);
    List<Comment> findByTravelIdAndIsDeletedOrderByCreatedAtDesc(Long id, Boolean bool);

//    Long findByTravelId(Long id);

    Comment findByIdAndIsDeleted(Long travelId, Boolean bool);

    @Query(value = "UPDATE owntwoday.comment SET is_deleted = :bool where id = :commentId")
    Comment updateByCommentId(Long commentId, Boolean bool);
}