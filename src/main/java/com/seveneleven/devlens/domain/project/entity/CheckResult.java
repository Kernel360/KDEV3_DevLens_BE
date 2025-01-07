package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import com.seveneleven.devlens.global.entity.YesNo;
import com.seveneleven.devlens.global.entity.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    private approvalStatus approvalStatus; // 승인 여부(결과)

    private Long processorMemberId; // 처리자

    private String processorIp; // 처리자 IP

    private LocalDateTime processedAt; // 처리 일시

    @Column(columnDefinition = "TEXT")
    private String rejectionReason; // 거부 사유

    @Column(nullable = false)
    @Convert(converter = YesNoConverter.class)
    private YesNo hasFile; // 파일 유무

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNo hasLink; // 링크 유무

    enum approvalStatus {
        WAITING,
        APPROVED,
        REJECTED;
    }
}