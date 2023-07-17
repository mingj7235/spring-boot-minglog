package com.minglog.api.controller;

import com.minglog.api.request.PostCreate;
import com.minglog.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate request) throws Exception {
        postService.write(request);
        return Map.of();
    }
}
