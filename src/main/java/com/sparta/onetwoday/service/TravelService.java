package com.sparta.onetwoday.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.onetwoday.dto.*;
import com.sparta.onetwoday.entity.Travel;
import com.sparta.onetwoday.entity.TravelLike;
import com.sparta.onetwoday.entity.User;
import com.sparta.onetwoday.entity.UserRoleEnum;
import com.sparta.onetwoday.repository.TravelLikeRepository;
import com.sparta.onetwoday.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sparta.onetwoday.entity.ExceptionMessage.*;
import static com.sparta.onetwoday.entity.SuccessMessage.*;


@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;
    private final TravelLikeRepository travelLikeRepository;

    private final CommentService commentService;

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    //게시물 작성하기
    @Transactional
    public ResponseEntity<Message> createTravel(@RequestBody TravelRequestDto requestDto, User user) throws IOException {
        Integer budget = budgetReturn(requestDto);
        String fileName = "";
        System.out.println("images : "+requestDto.getImages().getOriginalFilename());
        if(!(requestDto.getImages().getOriginalFilename().equals("") || requestDto.getImages().getOriginalFilename() == null)) {
            fileName = UUID.randomUUID() + "_" + requestDto.getImages().getOriginalFilename();
            s3ImageUpload(requestDto.getImages(), fileName);
        }
        else
            throw new CustomException(IMAGE_INVALID);
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

        if (budget == null) {
            throw new CustomException(BUDGET_INVALID_RANGE);
        }

        Travel travel = travelRepository.saveAndFlush(new Travel(requestDto, user, budget, amazonS3Client.getUrl(bucketName, fileName).toString()));

        return new Message().toResponseEntity(BOARD_POST_SUCCESS, new TravelResponseDto(travel));
    }

    //나의 게시물 리스트 조회하기
    @Transactional
    public ResponseEntity<Message> getMyList(User user) {

        List<Travel> travels = travelRepository.findAllByUserAndIsDeleted(user,false);
        List<TravelListResponseDto> travelListResponseDtos = new ArrayList<>();
        if(!travels.isEmpty()) {
            for (Travel travel : travels) {
//            List<CommentResponseDto> commentResponseDtos = new commentService.getCommentList(travel.getId())
                Long likes = travelLikeRepository.countByTravelId(travel.getId());
                travelListResponseDtos.add(new TravelListResponseDto(travel, likes));
            }
        }


        return new Message().toResponseEntity(BOARD_MY_LIST_GET_SUCCESS, travelListResponseDtos);

//        return travels.stream().map(travel -> new TravelListResponseDto((Travel) travels,likes)).collect(Collectors.toList());

    }

    //무작위(랜던) 리스트 8개 조회하기
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getRandomList() {
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

        Long count = travelRepository.countByIsDeleted(false);
        if (count < 8) {
            List<Travel> travels = travelRepository.findAllByIsDeleted(false);
            List<TravelListResponseDto> responseDtos = new ArrayList<>();
            for (Travel travel : travels) {
                //            List<CommentResponseDto> commentResponseDtos = new commentService.getCommentList(travel.getId())
                Long likes = travelLikeRepository.countByTravelId(travel.getId());
                responseDtos.add(new TravelListResponseDto(travel, likes));

            }

            return new Message().toResponseEntity(BOARD_GET_SUCCESS, responseDtos);
        }
        List<TravelListResponseDto> response = new ArrayList<>();

        List<Travel> travels = travelRepository.findAllByIsDeleted(false);

        for (Travel i : travels) {
            Long likes = travelLikeRepository.countByTravelId(i.getId());
            TravelListResponseDto travelListResponse = new TravelListResponseDto(i.getId(), i.getTitle(), i.getImages(), likes);
            response.add(travelListResponse);
        }


        return new Message().toResponseEntity(BOARD_GET_SUCCESS, response);

    }

    //상세 조회하기
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getDetail(Long travelId) {
        if(travelRepository.findByIdAndIsDeleted(travelId, false) == null) {
            throw new CustomException(BOARD_NOT_FOUND);
        }
        Travel travel = travelRepository.findByIdAndIsDeleted(travelId, false);
        List<CommentResponseDto> commentResponseDtos = commentService.getCommentList(travel.getId());
        Long likes = travelLikeRepository.countByTravelId(travel.getId());

        return new Message().toResponseEntity(BOARD_DETAIL_GET_SUCCESS, new TravelCommentDto(travel, likes, commentResponseDtos));
    }

    //게시물 수정하기
    @Transactional
    public ResponseEntity<Message> updateTravel(Long travelId, TravelRequestDto requestDto, User user) throws IOException {
        String fileName = requestDto.getImages().getOriginalFilename();

        if(travelRepository.findByIdAndIsDeleted(travelId, false) == null) {
            throw new CustomException(BOARD_NOT_FOUND);
        }
        Travel travel = travelRepository.findByIdAndIsDeleted(travelId, false);

        Integer budget = budgetReturn(requestDto);
        if (budget == null) {
            throw new CustomException(BUDGET_INVALID_RANGE);
        }
        
        //요청받은거랑 db 이미지명이 같을경우
        if(!fileName.equals(travel.getImages())){
            fileName = UUID.randomUUID() + "_" + requestDto.getImages().getOriginalFilename();
            s3ImageUpload(requestDto.getImages(), fileName);
        }

        if (hasAuthority(user, travel)) {
            travel.update(requestDto, budget, amazonS3Client.getUrl(bucketName, fileName).toString());
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
        List<CommentResponseDto> commentResponseDtos = commentService.getCommentList(travel.getId());
        Long likes = travelLikeRepository.countByTravelId(travel.getId());

        return new Message().toResponseEntity(BOARD_DETAIL_GET_SUCCESS, new TravelCommentDto(travel, likes, commentResponseDtos));
    }

    //게시물 삭제하기
    @Transactional
    public ResponseEntity<Message> deleteTravel(Long travelId, User user) {
        if(travelRepository.findByIdAndIsDeleted(travelId, false) == null) {
            throw new CustomException(BOARD_NOT_FOUND);
        }
        Travel travel = travelRepository.findByIdAndIsDeleted(travelId, false);
        if (hasAuthority(user, travel)) {
//            commentRepository.deleteByTravelId(travelId);
            travel.setIsDeleted();
            travelRepository.saveAndFlush(travel);
            return Message.toResponseEntity(BOARD_DELETE_SUCCESS);

        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }
    }

    //게시물 좋아요
    @Transactional
    public ResponseEntity<Message> likeTravel(Long travelId, User user) {
        if(travelRepository.findByIdAndIsDeleted(travelId, false) == null) {
            throw new CustomException(BOARD_NOT_FOUND);
        }
        Travel travel = travelRepository.findByIdAndIsDeleted(travelId, false);

        if (travelLikeRepository.findByUserIdAndTravelId(user.getId(), travelId).isEmpty()) {
            travelLikeRepository.saveAndFlush(new TravelLike(travel, user));
            return Message.toResponseEntity(LIKE_POST_SUCCESS);
        } else {
            travelLikeRepository.deleteByUserIdAndTravelId(user.getId(), travelId);
            return Message.toResponseEntity(LIKE_DELETE_SUCCESS);
        }
    }

    //예산 출력하기
    public Integer budgetReturn(TravelRequestDto requestDto) {
        return switch (requestDto.getBudget()) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> 4;
            default -> null;
        };

    }

    //권한 확인하기
    public boolean hasAuthority(User user, Travel travel) {
        return user.getId().equals(travel.getUser().getId()) || user.getRole().equals(UserRoleEnum.ADMIN);
    }

    public void s3ImageUpload(MultipartFile images, String fileName) throws IOException {
        if(!fileName.equals("")) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(images.getContentType());

            try (InputStream inputStream = images.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new CustomException(IMAGE_INVALID);
            }
        }
    }
}
