package com.sparta.onetwoday.service;

import com.sparta.onetwoday.dto.TravelRequestDto;
import com.sparta.onetwoday.dto.TravelResponseDto;
import com.sparta.onetwoday.entity.Travel;
import com.sparta.onetwoday.entity.User;
import com.sparta.onetwoday.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
