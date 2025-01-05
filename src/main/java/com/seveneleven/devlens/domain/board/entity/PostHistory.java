package com.seveneleven.devlens.domain.board.entity;

import com.seveneleven.devlens.domain.board.dto.PostStatus;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.domain.project.entity.ProjectStep;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_history")
public class PostHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 게시물 이력 ID

    @Column(name = "post_id", nullable = false)
    private Long postId; // 게시물 ID

    @Column(name = "project_step_id", nullable = false)
    private Long projectStepId; // 프로젝트 단계 ID

    @Column(name = "parent_post_id")
    private Long parentPostId; // 부모 게시물 ID

    @Column(name = "is_pinned_post")
    private Boolean isPinnedPost; // 상단고정 여부

    @Column(name = "priority")
    private Integer priority; // 우선순위 (1,2,3)

    @Column(name = "status", nullable = false, length = 50)
    private PostStatus status; // 상태 (선택(기본값), 진행, 추가, 완료, 보류)

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

    @Column(name = "registered_ip", length = 50)
    private String registeredIp; // 등록자 IP

    @Column(name = "modifier_ip", length = 50)
    private String modifierIp; // 수정자 IP

}
