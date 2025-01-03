package com.seveneleven.devlens.domain.board.entity;

import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 댓글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, referencedColumnName = "id")
    private Post postId; // 게시물 ID

    @JoinColumn(name = "parent_comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentCommentId; // 부모 댓글 ID

    @Column(name = "is_active", nullable = false)
    private Boolean isActive; // 사용 유무 (Y, N)

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content; // 내용

    @Column(name = "register_ip", length = 50)
    private String registerIp; // 등록자 IP

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp; // 수정자 IP

}