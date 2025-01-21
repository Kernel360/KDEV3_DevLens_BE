package com.seveneleven.board.service;

import com.seveneleven.board.dto.PostCreateRequest;
import com.seveneleven.board.dto.PostListResponse;
import com.seveneleven.board.dto.PostResponse;
import com.seveneleven.board.dto.PostUpdateRequest;
import com.seveneleven.entity.board.constant.PostFilter;
import com.seveneleven.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PageResponse<PostListResponse> selectPostList(Long projectStepId, Integer page, String keyword, PostFilter filter);
    PostResponse selectPost(Long postId) throws Exception;
    void createPost(PostCreateRequest postCreateRequest, List<MultipartFile> files) throws Exception;
    void updatePost(PostUpdateRequest postUpdateRequest, List<MultipartFile> files) throws Exception;
}
