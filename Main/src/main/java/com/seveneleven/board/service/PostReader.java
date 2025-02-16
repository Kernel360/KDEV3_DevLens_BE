package com.seveneleven.board.service;

import com.seveneleven.board.dto.PostListResponse;
import com.seveneleven.board.dto.RelatedPostResponse;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.project.ProjectStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostReader {
    void existPost(Long postId);

    Post getPost(Long postId);

    List<RelatedPostResponse> getRelatedPosts(Long postId);

    Page<PostListResponse> getAllPostsNoStepId(List<Long> projectId, String keyword, String repoFilter, Pageable pageable);

    Page<PostListResponse> getPostsByProjectStepId(Long projectStepId, String keyword, String repoFilter, Pageable pageable);

    ProjectStep getProjectStep(Long projectStepId);

    String getWriter(Long memberId);

    Long getRef(Long postId);

    List<Long> getProjectStepsId(Long projectId);

    Long getMaxRef();

    Integer getRefOrder(Post parentPost);
}
