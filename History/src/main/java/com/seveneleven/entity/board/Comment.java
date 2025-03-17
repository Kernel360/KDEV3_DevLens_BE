package com.seveneleven.entity.board;

import com.seveneleven.entity.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends BaseEntity {

    /*
        id : 댓글 ID
        postId : 게시물 ID
        parentCommentId : 부모 댓글 ID
        ref : 댓글 그룹 구분
        refOrder : 댓글 그룹 순서
        childCommentNum : 자식 댓글의 수
        content : 내용
        writer : 작성자 이름
        registerIp : 등록자 IP
        modifierIp : 수정자 IP
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, referencedColumnName = "id")
    private Post post;

    @JoinColumn(name = "parent_comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentComment;

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

    @Column(name = "register_ip", length = 50)
    private String registerIp;

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp;

    private Comment (Long id,
                     Post post,
                     Comment parentComment,
                     Long ref,
                     Integer refOrder,
                     Integer childCommentNum,
                     String content,
                     String writer,
                     String registerIp,
                     String modifierIp
    ) {
        this.id = id;
        this.post = post;
        this.parentComment = parentComment;
        this.ref = ref;
        this.refOrder = refOrder;
        this.childCommentNum = childCommentNum;
        this.content = content;
        this.writer = writer;
        this.registerIp = registerIp;
        this.modifierIp = modifierIp;
    }

    // 생성 메서드
    public static Comment createComment(
            Post post,
            Comment parentComment,
            Long ref,
            Integer refOrder,
            Integer childCommentNum,
            String content,
            String writer,
            String registerIp
    ) {
        Comment comment = new Comment();
        comment.post = post;
        comment.parentComment = parentComment;
        comment.ref = ref;
        comment.refOrder = refOrder;
        comment.childCommentNum = childCommentNum;
        comment.content = content;
        comment.writer = writer;
        comment.registerIp = registerIp;
        comment.modifierIp = null;
        return comment;
    }

    // 수정 메서드
    public void updateComment(
            String content,
            String modifierIp
    ) {
        this.content = content;
        this.modifierIp = modifierIp;
    }

    // 대댓글 생성 시 child_comment_num 증가
    public void increaseChildCommentNum() {
        this.childCommentNum++;
    }

    // 대댓글 삭제 시 child_comment_num 삭제
    public void decreaseChildCommentNum() {
        this.childCommentNum--;
    }
}