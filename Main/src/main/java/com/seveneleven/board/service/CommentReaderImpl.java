package com.seveneleven.board.service;

import com.seveneleven.board.dto.GetCommentResponse;
import com.seveneleven.board.repository.CommentRepository;
import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Comment;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.seveneleven.response.ErrorCode.NOT_FOUND_COMMENT;
import static com.seveneleven.response.ErrorCode.NOT_FOUND_POST;

@Component
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getComments(Long postId) {
        return commentRepository.getCommentList(postId);
    }

    @Override
    public List<GetCommentResponse> getIsActiveComments(Long postId) {
        List<Comment> comments = commentRepository.getCommentList(postId);
        List<GetCommentResponse> responses = new ArrayList<>();
        comments.stream()
                .map(comment ->
                        {
                            if(comment.getModifierIp() == null) {
                                responses.add(GetCommentResponse.toDto(comment, YesNo.NO));
                            } else {
                                responses.add(GetCommentResponse.toDto(comment, YesNo.YES));
                            }
                            return null;
                        }
                )
                .toList();
        return responses;
    }

    @Override
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
    }

    @Override
    public Long getMaxRef() {
        return commentRepository.findMaxRef();
    }

}
