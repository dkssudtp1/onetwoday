package com.sparta.onetwoday.repository;

import com.sparta.onetwoday.entity.TravelLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TravelLikeRepository extends JpaRepository<TravelLike, Long> {
    Optional<TravelLike> findByUserIdAndTravelId(Long userId, Long travelId);

//    void deleteByUserIdandTravelId(Long userId, Long travelId);
    Long countByTravelId(Long travelId);
}
