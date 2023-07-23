package com.minglog.api.controller;

import com.minglog.api.request.PostCreate;
import com.minglog.api.response.PostResponse;
import com.minglog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request){
        postService.write(request);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList (Pageable pageable) {
        return postService.getList(pageable);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }
}
