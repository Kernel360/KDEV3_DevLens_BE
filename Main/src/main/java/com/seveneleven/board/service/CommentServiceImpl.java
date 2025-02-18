package com.seveneleven.board.service;

import com.seveneleven.board.dto.GetCommentResponse;
import com.seveneleven.board.dto.PatchCommentRequest;
import com.seveneleven.board.dto.PostCommentRequest;
import com.seveneleven.entity.board.Comment;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.board.constant.HistoryAction;
import com.seveneleven.entity.member.constant.Role;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.notification.service.NotificationService;
import com.seveneleven.util.GetIpUtil;
import com.seveneleven.util.security.dto.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seveneleven.response.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentReader commentReader;
    private final CommentStore commentStore;
    private final PostReader postReader;
    private final GetIpUtil getIpUtil;
    private final NotificationService notificationService;

    /**
     * 함수명 : selectCommentList()
     * 함수 목적 : 댓글 목록을 조회하는 메서드
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetCommentResponse> selectCommentList(Long postId, Long userId){
        return commentReader.getIsActiveComments(postId, userId);
    }

    /**
     * 함수명 : createComment()
     * 함수 목적 : 댓글을 생성하는 메서드
     */
    @Transactional
    @Override
    public void createComment(Long postId, PostCommentRequest postCommentRequest, HttpServletRequest request, String registerName) {
        Post post = postReader.getPost(postId);
        String registerIp = getIpUtil.getIpAddress(request);
        Comment comment;

        // 댓글인 경우
        if(postCommentRequest.getParentCommentId() == null) {
            comment = saveComment(
                    post,
                    null,
                    commentReader.getMaxRef() + 1,
                    0,
                    postCommentRequest.getContent(),
                    registerName,
                    registerIp);
        } else {
        // 대댓글인 경우
            Comment parentComment = commentReader.getComment(postCommentRequest.getParentCommentId());
            comment = saveComment(
                    post,
                    parentComment,
                    parentComment.getRef(),
                    parentComment.getChildCommentNum() + 1,
                    postCommentRequest.getContent(),
                    registerName,
                    registerIp);

            parentComment.increaseChildCommentNum();
        }
        commentStore.storeCommentHistory(comment, HistoryAction.CREATE, registerIp, null);

        // 게시글 작성자에게 댓글 알림 전송
        notificationService.notifyComment(postId);
    }

    /**
     * 함수명 : updateComment()
     * 함수 목적 : 댓글을 수정하는 메서드
     */
    @Transactional
    @Override
    public void updateComment(Long postId, Long commentId, PatchCommentRequest patchCommentRequest, HttpServletRequest request, CustomUserDetails user) {
        postReader.existPost(postId);
        Comment comment = commentReader.getComment(commentId);

        checkCommentEditPermission(comment.getCreatedBy(), user);

        commentStore.storeCommentHistory(comment, HistoryAction.UPDATE, comment.getRegisterIp(), getIpUtil.getIpAddress(request));
        comment.updateComment(patchCommentRequest.getContent(), getIpUtil.getIpAddress(request));
    }

    /**
     * 함수명 : deleteComment()
     * 함수 목적 : 댓글을 삭제하는 메서드
     */
    @Transactional
    @Override
    public void deleteComment(Long postId, Long commentId, HttpServletRequest request, CustomUserDetails user) {
        postReader.existPost(postId);
        Comment comment = commentReader.getComment(commentId);

        checkCommentEditPermission(comment.getCreatedBy(), user);

        commentStore.storeCommentHistory(comment, HistoryAction.DELETE, comment.getRegisterIp(), getIpUtil.getIpAddress(request));

        // 댓글인 경우
        if(comment.getParentComment() == null) {
            // 대댓글이 존재하는 경우, 댓글 삭제 불가능
            if(comment.getChildCommentNum() >= 1) {
                throw new BusinessException(NOT_DELETE_PARENT_COMMENT);
            }
        } else {
        // 대댓글인 경우
            comment.getParentComment().decreaseChildCommentNum();
        }
        commentStore.deleteComment(commentId);
    }

    /**
     * 함수명 : deleteComments()
     * 함수 목적 : 게시글 삭제 시 댓글을 일괄 삭제하는 메서드
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteAllComments(Post post, String deleterIp) {
        List<Comment> comments = commentReader.getComments(post.getId());

        comments.forEach(comment -> {
            commentStore.storeCommentHistory(comment, HistoryAction.DELETE, comment.getRegisterIp(), deleterIp);
            commentStore.deleteComment(comment.getId());
        });
    }

    /**
     * 함수명 : saveComment()
     * 함수 목적 : 댓글 또는 대댓글 구분값으로 댓글을 생성하는 메서드
     */
    private Comment saveComment(Post post, Comment parentComment, Long ref, Integer refOrder, String content, String writer, String registerIp) {
        return commentStore.storeComment(
                Comment.createComment(
                        post,
                        parentComment,
                        ref,
                        refOrder,
                        0,
                        content,
                        writer,
                        registerIp
                )
        );
    }

    /**
     * 함수명 : checkCommentEditPermission()
     * 함수 목적 : 댓글 작성자 또는 관리자 일치 여부 확인
     */
    private void checkCommentEditPermission(Long commentCreatedBy, CustomUserDetails user) {
        // 작성자 또는 관리자가 아닌 경우
        if(!commentCreatedBy.equals(user.getMember().getId()) && !(Role.ADMIN.equals(user.getMember().getRole()))) {
            throw new BusinessException(NOT_HAVE_EDIT_PERMISSION);
        }
    }
}