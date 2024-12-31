package com.seveneleven.devlens.domain.board.entity;

import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.project.entity.ProjectStep;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId; // 게시물 ID (문서번호)

    @JoinColumn(name = "project_step_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectStep projectStepId; // 프로젝트 단계 ID

    @JoinColumn(name = "parent_post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post parentPostId; // 부모 게시물 ID

    @Column(name = "is_pinned_post")
    private Boolean isPinnedPost; // 상단고정 여부

    @Column(name = "status", nullable = false, length = 50)
    private String status; // 상태 (기본, 요청, 진행, 피드백, 완료, 보류)

    @Column(name = "title", nullable = false, length = 255)
    private String title; // 제목

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // 내용

    @Column(name = "has_file", nullable = false)
    private Boolean hasFile; // 파일 유무

    @Column(name = "has_link", nullable = false)
    private Boolean hasLink; // 링크 유무

    @Column(name = "deadline")
    private LocalDate deadline; // 마감일자

    @CreatedBy
    @JoinColumn(name = "register_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member registerId; // 등록자 ID

    @Column(name = "register_ip", length = 50)
    private String registerIp; // 등록자 IP

    @CreatedDate
    @Column(name = "registered_date")
    private LocalDateTime registeredDate; // 등록일시

    @LastModifiedBy
    @JoinColumn(name = "modifier_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member modifierId; // 수정자

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp; // 수정자 IP

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate; // 수정일시
}