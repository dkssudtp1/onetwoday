package com.sparta.onetwoday.service;

import com.sparta.onetwoday.dto.TravelCommentDto;
import com.sparta.onetwoday.dto.TravelListResponseDto;
import com.sparta.onetwoday.dto.TravelRequestDto;
import com.sparta.onetwoday.dto.TravelResponseDto;
import com.sparta.onetwoday.entity.Travel;
import com.sparta.onetwoday.entity.TravelLike;
import com.sparta.onetwoday.entity.User;
import com.sparta.onetwoday.entity.UserRoleEnum;
import com.sparta.onetwoday.repository.TravelLikeRepository;
import com.sparta.onetwoday.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;
    private final TravelLikeRepository travelLikeRepository;


    //게시물 작성하기
    @Transactional
    public TravelResponseDto createTravel(@RequestBody TravelRequestDto requestDto, User user) {
        Integer budget = budgetReturn(requestDto);

//        switch (requestDto.getBudget()) {
//            case 0:
//                budget = 0;
//                break;
//            case 1:
//                budget = 1;
//                break;
//            case 2:
//                budget = 2;
//                break;
//            case 3:
//                budget = 3;
//                break;
//        }

        if(budget == null) {
            throw new NullPointerException("유효한 범위 내에 있는 예산이 아닙니다.");
        }

        Travel travel = travelRepository.saveAndFlush(new Travel(requestDto, user, budget));
        return new TravelResponseDto(travel);
    }

    //나의 게시물 리스트 조회하기
    @Transactional
    public List<TravelListResponseDto> getMyList(User user) {

        List<Travel> travels = travelRepository.findAllByUser(user);
        List<TravelListResponseDto> travelListResponseDtos = new ArrayList<>();
        for(Travel travel : travels) {
//            List<CommentResponseDto> commentResponseDtos = new commentService.getCommentList(travel.getId())
            Long likes = travelLikeRepository.countByTravelId(travel.getId());
            travelListResponseDtos.add(new TravelListResponseDto(travel, likes));
        }

        return travelListResponseDtos;

//        return travels.stream().map(travel -> new TravelListResponseDto((Travel) travels,likes)).collect(Collectors.toList());

    }

    //무작위(랜던) 리스트 8개 조회하기
    @Transactional(readOnly = true)
    public List<TravelListResponseDto> getRandomList() {
//        Long count = travelRepository.countBy();
//        List<TravelListResponseDto> travelListResponse = new ArrayList<>();
//        if(count < 8) {
//            List<Travel> travels = travelRepository.findAll();
//            return travels.stream().map(TravelListResponseDto::new).collect(Collectors.toList());
//        }
//
//        for(int i = 1; i <= 8; i++) {
//            Long id = (long)(Math.random() * count);
//            Optional<Travel> travel = travelRepository.findById(id);
//            travelListResponse.add((TravelListResponseDto) travel.stream().map(TravelListResponseDto::new));
//        }

//
//        return travelListResponse;

        Long count = travelRepository.countBy();
        if(count < 8) {
            List<Travel> travels = travelRepository.findAll();
            List<TravelListResponseDto> responseDtos = new ArrayList<>();
            for(Travel travel : travels) {
                //            List<CommentResponseDto> commentResponseDtos = new commentService.getCommentList(travel.getId())
                Long likes = travelLikeRepository.countByTravelId(travel.getId());
                responseDtos.add(new TravelListResponseDto(travel, likes));

            }

            return responseDtos;
        }
        List<TravelListResponseDto> response = new ArrayList<>();

        List<Travel> travels = travelRepository.findAll();

        for(Travel i : travels) {
            Long likes = travelLikeRepository.countByTravelId(i.getId());
            TravelListResponseDto travelListResponse = new TravelListResponseDto(i.getId(), i.getTitle(), i.getImages(), likes);
            response.add(travelListResponse);
        }


        return response;

    }

    //상세 조회하기
    @Transactional(readOnly = true)
    public TravelCommentDto getDetail(Long travelId) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );
//        List<CommentResponseDto> commentResponseDtos = commentService.getCommnetList(travel.getId());
        Long likes = travelLikeRepository.countByTravelId(travel.getId());
        return new TravelCommentDto(travel, likes, " ");
    }

    //게시물 수정하기
    @Transactional
    public TravelCommentDto updateTravel(Long travelId, TravelRequestDto requestDto, User user) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );
        Integer budget = budgetReturn(requestDto);
        if(budget == null) {
            throw new NullPointerException("유효한 범위 내에 있는 예산이 아닙니다.");
        }

        if (hasAuthority(user, travel)) {
            travel.update(requestDto, budget);
        } else {
            throw new IllegalArgumentException("작성자만 수정/삭제할 수 있습니다.");
        }
        Long likes = travelLikeRepository.countByTravelId(travel.getId());
        return new TravelCommentDto(travel, likes, " ");
    }

    //게시물 삭제하기
    @Transactional
    public void deleteTravel(Long travelId, User user) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );
        if(hasAuthority(user, travel)) {
//            commentRepository.deleteByTravelId(travelId);
            travelRepository.deleteById(travelId);
        } else {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }
    
    //게시물 좋아요
    @Transactional
    public String likeTravel(Long travelId, User user) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );
        if (travelLikeRepository.findByUserIdAndTravelId(user.getId(), travelId).isEmpty()) {
            travelLikeRepository.saveAndFlush(new TravelLike(travel,user));
            return "좋아요를 하셨습니다.";
        } else {
            travelLikeRepository.findByUserIdAndTravelId(user.getId(), travelId);
            return "좋아요를 취소하셨습니다.";
        }
    }

    //예산 출력하기
    public Integer budgetReturn(TravelRequestDto requestDto) {
        return switch (requestDto.getBudget()) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            default -> null;
        };

    }

    //권한 확인하기
    public boolean hasAuthority(User user, Travel travel) {
        return user.getId().equals(travel.getUser().getId()) || user.getRole().equals(UserRoleEnum.ADMIN);
    }

}
