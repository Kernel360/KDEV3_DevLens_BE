package com.seveneleven.board.service;

import com.seveneleven.board.dto.PostListResponse;
import com.seveneleven.board.dto.RelatedPostResponse;
import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.seveneleven.response.ErrorCode.*;

@Component
@RequiredArgsConstructor
public class PostReaderImpl implements PostReader {
    private final PostRepository postRepository;
    private final ProjectStepRepository projectStepRepository;
    private final MemberRepository memberRepository;

    @Override
    public void existPost(Long postId) {
        if(!postRepository.existsById(postId)) {
            throw new BusinessException(NOT_FOUND_POST);
        }
    }

    @Override
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    @Override
    public List<RelatedPostResponse> getRelatedPosts(Long postId) {
        return postRepository.findChildPostIdsAndTitlesByParentId(postId);
    }

    @Override
    public Page<PostListResponse> getAllPostsNoStepId(List<Long> projectStepIds, String keyword, String repoFilter, Pageable pageable) {
        return postRepository.findAllPosts(projectStepIds, keyword, repoFilter, pageable);
    }

    @Override
    public Page<PostListResponse> getPostsByProjectStepId(Long projectStepId, String keyword, String repoFilter, Pageable pageable) {
        return postRepository.findAllByProjectStepId(projectStepId, keyword, repoFilter, pageable);
    }

    @Override
    public Long getRef(Long postId) {
        return postRepository.findRefById(postId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    @Override
    public Long getMaxRef() {
        return postRepository.findMaxRef();
    }

    @Override
    public Integer getRefOrder(Post parentPost) {
        if(parentPost.getChildPostNum().equals(0)) {
            return 0;
        }
        return postRepository.findMaxRefOrderByParentPostId(parentPost.getId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }


    @Override
    public List<Long> getProjectStepsId(Long projectId) {
        return projectStepRepository.findAllProjectStepIds(projectId);
    }

    @Override
    public ProjectStep getProjectStep(Long projectStepId) {
        return projectStepRepository.findById(projectStepId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_PROJECT_STEP));
    }

    @Override
    public String getWriter(Long memberId) {
        return memberRepository.findNameById(memberId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_WRITER));
    }


}
