package com.minglog.api.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEditor {

    private final String title;
    private final String content;

    @Builder
    public PostEditor(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

}
