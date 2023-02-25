package com.sparta.onetwoday.entity;

import com.sparta.onetwoday.dto.TravelRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERS_ID")
    private User user;

//    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
//    private List<Comment> comment = new ArrayList<>();

    @Column
    private Integer budget;

    public Travel(TravelRequestDto requestDto, User user, Integer budget) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.images = requestDto.getImages();
        this.budget = budget;
        this.user = user;
    }

}
