package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "check_result_history")
public class CheckResultHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // ID

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 요청 내용

    @Column(name = "approval_status", length = 50)
    private String approvalStatus; // 승인 여부(결과)

    @Column(name = "processor_id", nullable = false)
    private Long processorId; // 처리자 ID

    @Column(name = "processor_ip", length = 50)
    private String processorIp; // 처리자 IP

    @Column(name = "processed_at")
    private LocalDateTime processedAt; // 처리 일시

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason; // 거부 사유

    @Column(name = "has_file", nullable = false)
    private Boolean hasFile; // 파일 유무

    @Column(name = "has_link", nullable = false)
    private Boolean hasLink; // 링크 유무

    @Column(name = "is_active", nullable = false)
    private Boolean isActive; // 사용 유무
}