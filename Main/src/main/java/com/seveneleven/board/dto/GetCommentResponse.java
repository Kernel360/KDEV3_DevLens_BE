package com.seveneleven.board.dto;

import com.seveneleven.entity.board.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCommentResponse {
    Long commentId;
    Long parentCommentId;
    Long registerId;
    String writer;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    private GetCommentResponse(Long commentId,
                               Long parentCommentId,
                               Long registerId,
                               String writer,
                               String content,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.parentCommentId = parentCommentId;
        this.registerId = registerId;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Comment 를 GetCommentResponse 로 변환
    public static GetCommentResponse toDto(Comment comment, String writer) {
        return new GetCommentResponse(
                comment.getId(),
                getParentCommentId(comment.getParentCommentId()),
                comment.getCreatedBy(),
                writer,
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    private static Long getParentCommentId(Comment parentCommentId) {
        if(parentCommentId != null) {
            return parentCommentId.getId();
        }
        return null;
    }
}
