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
@Table(name = "check_result")
public class CheckResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 체크 결과 ID

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_request_id", nullable = false)
    private CheckRequest checkRequest; // 체크 요청 ID

    @Column(name = "approval_status", length = 50)
    private String approvalStatus; // 승인 여부(결과)

    @JoinColumn(name = "processor_member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member processorMemberId; // 처리자

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
}