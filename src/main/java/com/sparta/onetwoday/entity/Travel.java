package com.sparta.onetwoday.entity;

import com.sparta.onetwoday.dto.TravelRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity(name = "travel")
@Getter
@NoArgsConstructor
public class Travel extends TimeStamped {
    @Id @Column(name = "TRAVEL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String images;

    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERS_ID")
    private User user;

    @Column
    private Integer budget;

    public Travel(TravelRequestDto requestDto, User user, Integer budget, String imgUrl) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.images = imgUrl;
        this.budget = budget;
        this.user = user;
        this.isDeleted = false;
    }

    public void update(TravelRequestDto requestDto, Integer budget, String imgUrl) {
        this.title = requestDto.getTitle();
        this.images = imgUrl;
        this.content = requestDto.getContent();
        this.budget = budget;
    }

    public void setIsDeleted() {
        this.isDeleted = true;
    }
}
