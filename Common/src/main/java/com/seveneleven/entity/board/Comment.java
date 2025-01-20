package com.seveneleven.entity.board;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.global.converter.YesNoConverter;
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
        isActive : 사용 유무 (Y, N)
        content : 내용
        registerIp : 등록자 IP
        modifierIp : 수정자 IP
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, referencedColumnName = "id")
    private Post postId;

    @JoinColumn(name = "parent_comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentCommentId;

    @Column(name = "ref")
    private Long ref;

    @Column(name = "ref_order")
    private Integer refOrder;

    @Column(name = "child_comment_num")
    private Integer childCommentNum;

    @Column(name = "is_active", nullable = false)
    @Convert(converter = YesNoConverter.class)
    @Enumerated(EnumType.STRING)
    private YesNo isActive;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

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
                    YesNo isActive,
                    String content,
                    String registerIp,
                    String modifierIp) {
        this.id = id;
        this.postId = post;
        this.parentCommentId = parentComment;
        this.ref = ref;
        this.refOrder = refOrder;
        this.childCommentNum = childCommentNum;
        this.isActive = isActive;
        this.content = content;
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
            String registerIp
    ) {
        Comment comment = new Comment();
        comment.postId = post;
        comment.parentCommentId = parentComment;
        comment.ref = ref;
        comment.refOrder = refOrder;
        comment.childCommentNum = childCommentNum;
        comment.isActive = YesNo.YES;
        comment.content = content;
        comment.registerIp = registerIp;
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

    // 삭제 메서드
    public void deleteComment(
            String modifierIp
    ) {
        this.isActive = YesNo.NO;
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