package com.seveneleven.board.service;

import com.seveneleven.board.dto.DeleteCommentRequest;
import com.seveneleven.board.dto.GetCommentResponse;
import com.seveneleven.board.dto.PatchCommentRequest;
import com.seveneleven.board.dto.PostCommentRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {

    @Transactional(readOnly = true)
    List<GetCommentResponse> selectCommentList(long postId);

    @Transactional
    void createComment(long postId, PostCommentRequest postCommentRequest) throws Exception;

    @Transactional
    void updateComment(Long postId, Long commentId, PatchCommentRequest patchCommentRequest) throws Exception;

    @Transactional
    void deleteComment(Long postId, Long commentId, DeleteCommentRequest deleteCommentRequest) throws Exception;
}
