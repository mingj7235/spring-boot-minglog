package com.minglog.api.service;

import com.minglog.api.domain.Post;
import com.minglog.api.repository.PostRepository;
import com.minglog.api.request.PostCreate;
import com.minglog.api.request.PostEdit;
import com.minglog.api.request.PostSearch;
import com.minglog.api.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        Assertions.assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        //given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);

        // when
        PostResponse response = postService.get(requestPost.getId());

        // then
        assertNotNull(response);
        assertEquals(1L, postRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {
        //given

        List<Post> requestPosts = IntStream.range(0, 20) //for (int = 0; i < 30; i++)
                .mapToObj(i -> Post.builder()
                        .title("게시판 제목 - " + i)
                        .content("게시판 내용 - " + i)
                        .build())
                .toList();
        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        assertEquals(10L, posts.size());
        assertEquals("게시판 제목 - 19", posts.get(0).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        //given
        Post post = Post.builder()
                .title("게시판 제목")
                .content("게시판 내용")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("밍밍 제목")
                .content("게시판 내용")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new IllegalStateException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("밍밍 제목", changedPost.getTitle());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        //given
        Post post = Post.builder()
                .title("게시판 제목")
                .content("게시판 내용")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("수정")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new IllegalStateException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("수정", changedPost.getContent());
        assertEquals("게시판 제목", changedPost.getTitle());
    }
    @Test
    @DisplayName("글 내용 삭제")
    void test6() {
        //given
        Post post = Post.builder()
                .title("게시판 제목")
                .content("게시판 내용")
                .build();
        postRepository.save(post);

        // when
        postService.delete(post.getId());

        //then
        Assertions.assertEquals(0, postRepository.count());

    }
}