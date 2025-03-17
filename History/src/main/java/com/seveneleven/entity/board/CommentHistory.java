package com.seveneleven.entity.board;

import com.seveneleven.entity.board.constant.HistoryAction;
import com.seveneleven.entity.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment_history")
public class CommentHistory extends BaseEntity {

    /*
        id : 댓글 ID
        postId : 게시물 ID
        parentCommentId : 부모 댓글 ID
        ref : 댓글 그룹 구분
        refOrder : 댓글 그룹 순서
        childCommentNum : 자식 댓글의 수
        content : 내용
        writer : 작성자 이름
        action : 작업 종류 (CREATE, UPDATE, DELETE)
        registerIp : 등록자 IP
        modifierIp : 수정자 IP
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "ref")
    private Long ref;

    @Column(name = "ref_order")
    private Integer refOrder;

    @Column(name = "child_comment_num")
    private Integer childCommentNum;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "writer", nullable = false)
    private String writer;

    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private HistoryAction action; // 작업 종류 (CREATE, UPDATE, DELETE)

    @Column(name = "register_ip", length = 50)
    private String registerIp;

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp;

    private CommentHistory(
            Long postId,
            Long parentCommentId,
            Long ref,
            Integer refOrder,
            Integer childCommentNum,
            String content,
            String writer,
            HistoryAction action,
            String registerIp,
            String modifierIp
    ) {
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.ref = ref;
        this.refOrder = refOrder;
        this.childCommentNum = childCommentNum;
        this.content = content;
        this.writer = writer;
        this.action = action;
        this.registerIp = registerIp;
        this.modifierIp = modifierIp;
    }

    // 댓글 이력 생성
    public static CommentHistory createCommentHistory(Comment comment, HistoryAction action, String registerIp, String modifierIp) {
        return new CommentHistory(
                comment.getPost().getId(),
                getParentCommentId(comment),
                comment.getRef(),
                comment.getRefOrder(),
                comment.getChildCommentNum(),
                comment.getContent(),
                comment.getWriter(),
                action,
                registerIp,
                modifierIp
        );
    }

    private static Long getParentCommentId(Comment comment) {
        if (comment.getParentComment() != null) {
            return comment.getParentComment().getId();
        }
        return null;
    }
}
