package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.global.entity.BaseEntity;
import com.seveneleven.devlens.global.entity.YesNo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "check_result")
public class CheckResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 체크 결과 ID

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_request_id", nullable = false)
    private CheckRequest checkRequestId; // 체크 요청 ID

    @Column(length = 50)
    private String approvalStatus; // 승인 여부(결과)

    private Long processorMemberId; // 처리자

    private String processorIp; // 처리자 IP

    private LocalDateTime processedAt; // 처리 일시

    @Column(columnDefinition = "TEXT")
    private String rejectionReason; // 거부 사유

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNo hasFile; // 파일 유무

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNo hasLink; // 링크 유무
}