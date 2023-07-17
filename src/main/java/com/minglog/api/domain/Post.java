package com.minglog.api.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob // DB 에서 Long Text 타입
    private String content;

    public Post(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

}
