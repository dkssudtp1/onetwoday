package com.sparta.onetwoday.service;

import com.sparta.onetwoday.dto.TravelListResponseDto;
import com.sparta.onetwoday.dto.TravelRequestDto;
import com.sparta.onetwoday.dto.TravelResponseDto;
import com.sparta.onetwoday.entity.Travel;
import com.sparta.onetwoday.entity.User;
import com.sparta.onetwoday.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;

    @Transactional
    public TravelResponseDto createTravel(TravelRequestDto requestDto, User user) {
        Integer budget = null;

        switch (requestDto.getBudget()) {
            case 0:
                budget = 0;
                break;
            case 1:
                budget = 1;
                break;
            case 2:
                budget = 2;
                break;
            case 3:
                budget = 3;
                break;
        }

        if(budget == null) {
            throw new NullPointerException("유효한 범위 내에 있는 예산이 아닙니다.");
        }

        Travel travel = travelRepository.saveAndFlush(new Travel(requestDto, user, budget));
        return new TravelResponseDto(travel);
    }

    @Transactional
    public List<TravelListResponseDto> getMyList(User user) {

        List<Travel> travels = travelRepository.findAllByUser(user);

        return travels.stream().map(TravelListResponseDto::new).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<TravelListResponseDto> getRandomList() {
        Long count = travelRepository.countBy();
        List<TravelListResponseDto> travelListResponse = new ArrayList<>();
        if(count < 8) {
            List<Travel> travels = travelRepository.findAll();
            return travels.stream().map(TravelListResponseDto::new).collect(Collectors.toList());
        }

        for(int i = 1; i <= 8; i++) {
            Long id = (long)(Math.random() * count);
            Optional<Travel> travel = travelRepository.findById(id);
            travelListResponse.add((TravelListResponseDto) travel.stream().map(TravelListResponseDto::new));
        }

        return travelListResponse;


    }
}
