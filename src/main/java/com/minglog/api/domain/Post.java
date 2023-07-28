package com.minglog.api.domain;

import com.minglog.api.request.PostEdit;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    public Post(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public void edit(final PostEdit postEdit) {

        if (postEdit.getTitle() != null) {
            title = postEdit.getTitle();
        }

        if (postEdit.getContent() != null) {
            content = postEdit.getContent();
        }
    }
}
