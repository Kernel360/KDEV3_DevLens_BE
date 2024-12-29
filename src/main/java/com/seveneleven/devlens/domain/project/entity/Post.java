package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "id")
    private Long id; // 게시물 ID

    @JoinColumn(name = "project_step_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectStep projectStepId; // 프로젝트 단계 ID

    @JoinColumn(name = "parent_post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post parentPostId; // 부모 게시물 ID

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

    @JoinColumn(name = "register_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member register;

    @Column(name = "registered_ip", length = 50)
    private String registeredIp; // 등록자 IP

    @Column(name = "registerd_date")
    private LocalDateTime registerdDate; // 등록일

    @JoinColumn(name = "modified_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member modifier; // 수정자

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate; // 등록일

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp; // 수정자 IP
}