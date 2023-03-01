package com.sparta.onetwoday.repository;

import com.sparta.onetwoday.entity.Travel;
import com.sparta.onetwoday.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<Travel> findAllByOrderByCreatedAtDesc();
    List<Travel> findAllByUserAndIsDeleted(User user, Boolean bool);

//    Long countBy();
    @Query(value = "SELECT * FROM owntwoday.travel where is_deleted = :bool order by RAND() limit 8",nativeQuery = true)
    List<Travel> findAllByIsDeleted(Boolean bool);

    Travel findByIdAndIsDeleted(Long travelId, Boolean bool);
    Long countByIsDeleted(Boolean bool);
    @Query(value = "SELECT * FROM owntwoday.travel order by RAND() limit 8",nativeQuery = true)
    List<Travel> findAll();

    List<Travel> findAllByIsDeletedAndBudget(Boolean bool, int budget);
    Long countByIsDeletedAndBudget(Boolean bool, int budget);
}
