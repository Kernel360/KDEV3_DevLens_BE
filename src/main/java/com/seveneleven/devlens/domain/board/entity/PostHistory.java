package com.seveneleven.devlens.domain.board.entity;

import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.project.entity.ProjectStep;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "post_history")
public class PostHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_history_id")
    private Long postHistoryId; // 게시물 ID

    @Column(name = "project_step_id", nullable = false)
    private Long projectStepId; // 프로젝트 단계 ID

    @Column(name = "parent_post_id")
    private Long parentPostId; // 부모 게시물 ID

    @Column(name = "is_pinned_post")
    private Boolean isPinnedPost; // 고정 여부

    @Column(name = "status", nullable = false, length = 50)
    private String status; // 상태 (요청, 진행, 피드백, 완료, 보류)

    @Column(name = "title", nullable = false, length = 255)
    private String title; // 제목

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // 내용

    @Column(name = "has_file", nullable = false)
    private Boolean hasFile; // 파일 유무

    @Column(name = "has_link", nullable = false)
    private Boolean hasLink; // 링크 유무

    @CreatedBy
    @JoinColumn(name = "register_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member registerId;

    @Column(name = "registered_ip", length = 50)
    private String registeredIp; // 등록자 IP

    @CreatedDate
    @Column(name = "registered_date")
    private LocalDateTime registeredDate; // 등록일

    @LastModifiedBy
    @JoinColumn(name = "modified_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member modifier; // 수정자

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate; // 등록일

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp; // 수정자 IP
}
