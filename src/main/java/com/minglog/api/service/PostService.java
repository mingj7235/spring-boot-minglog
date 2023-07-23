package com.minglog.api.service;

import com.minglog.api.domain.Post;
import com.minglog.api.repository.PostRepository;
import com.minglog.api.request.PostCreate;
import com.minglog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(final Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 글 입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    /**
     * Paging 안하면..
     *
     * 글이 너무 많은 경우에 비용이 많이든다.
     * 1. DB에 너무 많은 글을 한번에 조회 시도 할 시, DB 가 뻗을 수 있다.
     * 2. WAS 로 전달하는 시간에 대한 트래픽 비용이 많이 발생 할 수 있다.
     */

    public List<PostResponse> getList(Pageable pageable) {
//        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        return postRepository.findAll(pageable).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

}
