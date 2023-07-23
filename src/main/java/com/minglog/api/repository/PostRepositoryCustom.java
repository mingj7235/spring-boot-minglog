package com.minglog.api.repository;

import com.minglog.api.domain.Post;
import com.minglog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

}
