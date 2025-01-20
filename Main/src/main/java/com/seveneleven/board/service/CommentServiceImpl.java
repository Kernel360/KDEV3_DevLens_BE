package com.seveneleven.board.service;

import com.seveneleven.board.dto.DeleteCommentRequest;
import com.seveneleven.board.dto.GetCommentResponse;
import com.seveneleven.board.dto.PatchCommentRequest;
import com.seveneleven.board.dto.PostCommentRequest;
import com.seveneleven.board.repository.CommentRepository;
import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Comment;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.member.Member;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seveneleven.response.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 함수명 : selectCommentList()
     * 함수 목적 : 댓글 목록을 조회하는 메서드
     */
    @Transactional(readOnly = true)
    @Override
    public List<GetCommentResponse> selectCommentList(long postId){
        // postId 존재 여부 확인
        Post post = getPost(postId);

        return commentRepository.getCommentList(post.getId())
                .stream()
                .map(comment -> {
                    String writer = memberRepository.findById(comment.getCreatedBy())
                            .map(Member::getName)
                            .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));
                    return GetCommentResponse.toDto(comment, writer);
                        })
                .toList();
    }

    /**
     * 함수명 : createComment()
     * 함수 목적 : 댓글을 생성하는 메서드
     */
    @Transactional
    @Override
    public void createComment(long postId, PostCommentRequest postCommentRequest) throws Exception {
        // postId 존재 여부 확인
        Post post = getPost(postId);

        // 댓글인 경우
        if(postCommentRequest.getParentCommentId() == null) {
            commentRepository.save(
                    Comment.createComment(
                            post,
                            null,
                            commentRepository.findMaxRef() + 1,
//                            (commentRepository.findMaxRef() != null ? commentRepository.findMaxRef() : 0) + 1,
                            0,
                            0,
                            postCommentRequest.getContent(),
                            postCommentRequest.getRegisterIp()
                    )
            );
        } else {
        // 대댓글인 경우
            Comment parentComment = commentRepository.findById(postCommentRequest.getParentCommentId())
                    .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
            commentRepository.save(
                    Comment.createComment(
                            post,
                            parentComment,
                            parentComment.getRef(),
                            parentComment.getChildCommentNum() + 1,
                            0,
                            postCommentRequest.getContent(),
                            postCommentRequest.getRegisterIp()
                    )
            );
            increaseChildCommentNum(postCommentRequest.getParentCommentId());
        }
    }

    /**
     * 함수명 : updateComment()
     * 함수 목적 : 댓글을 수정하는 메서드
     */
    @Transactional
    @Override
    public void updateComment(Long postId, Long commentId, PatchCommentRequest patchCommentRequest) throws Exception {
        // postId 및 commentId 존재 여부 확인
        getPost(postId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));

        // 작성자 일치 여부 확인
        matchCommentWriter(comment.getCreatedBy(), patchCommentRequest.getModifierId());

        // 기존 댓글 수정
        comment.updateComment(
                patchCommentRequest.getContent(),
                patchCommentRequest.getModifierIp()
        );
        commentRepository.save(comment);
    }

    /**
     * 함수명 : deleteComment()
     * 함수 목적 : 댓글을 삭제하는 메서드
     */
    @Transactional
    @Override
    public void deleteComment(Long postId, Long commentId, DeleteCommentRequest deleteCommentRequest) throws Exception {
        // postId 및 commentId 존재 여부 확인
        getPost(postId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));

        // 작성자 일치 여부 확인
        matchCommentWriter(comment.getCreatedBy(), deleteCommentRequest.getModifierId());

        // 댓글인 경우,
        if(comment.getParentCommentId() == null) {
            if(comment.getChildCommentNum() >= 1) {
                throw new BusinessException(NOT_DELETE_PARENT_COMMENT);
            }
            comment.deleteComment(deleteCommentRequest.getModifierIp());
            commentRepository.save(comment);
        } else {
        // 대댓글인 경우
            // 댓글의 child_comment_num - 1 감소
            decreaseChildCommentNum(comment.getParentCommentId().getId());
            comment.deleteComment(deleteCommentRequest.getModifierIp());
            commentRepository.save(comment);
        }
    }

//    /**
//     * 함수명 : saveComment()
//     * 함수 목적 : 댓글 생성 메서드
//     */
//    private void saveComment(Post post, Comment parentComment, Long ref, Integer refOrder, PostCommentRequest postCommentRequest) throws Exception {
//        commentRepository.save(
//                Comment.createComment(
//                        post,
//                        parentComment,
//                        ref,
//                        refOrder,
//                        0,
//                        postCommentRequest.getContent(),
//                        postCommentRequest.getRegisterIp()
//                )
//        );
//    }

    /**
     * 함수명 : getPost()
     * 함수 목적 : 게시글 존재 여부 확인
     */
    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_POST));
    }

    /**
     * 함수명 : matchCommentWriter()
     * 함수 목적 : 댓글 작성자 일치 여부 확인
     */
    private void matchCommentWriter(Long commentCreatedBy, Long modifierId) {
        if(!commentCreatedBy.equals(modifierId)) {
            throw new BusinessException(NOT_MATCH_WRITER);
        }
    }

    /**
     * 함수명 : increaseChildPostNum()
     * 함수 목적 : 답글 생성 시 child_post_num 증가
     */
    private void increaseChildCommentNum(Long parentCommentId) throws Exception {
        Comment comment = commentRepository.findById(parentCommentId).orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
        comment.increaseChildCommentNum();
        commentRepository.save(comment);
    }

    /**
     * 함수명 : decreaseChildPostNum()
     * 함수 목적 : 답글 삭제 시 child_post_num 감소
     */
    private void decreaseChildCommentNum(Long parentCommentId) throws Exception {
        Comment comment = commentRepository.findById(parentCommentId).orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));
        comment.decreaseChildCommentNum();
        commentRepository.save(comment);
    }
}
