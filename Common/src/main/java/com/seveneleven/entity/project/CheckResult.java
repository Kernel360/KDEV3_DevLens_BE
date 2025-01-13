package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.constant.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private CheckRequest checkRequest; // 체크 요청 ID

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus; // 승인 여부(결과)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check")
    private Member processorMember; // 처리자

    private String processorIp; // 처리자 IP

    @Column(columnDefinition = "TEXT")
    private String rejectionReason; // 거부 사유

    private CheckResult(CheckRequest checkRequest, Member processor, String processorIp) {
        // 승락 생성사
        this.checkRequest = checkRequest;
        this.processorMember = processor;
        this.processorIp = processorIp;
        approvalStatus = ApprovalStatus.APPROVED;
    }

    private CheckResult(CheckRequest checkRequest, Member processor, String processorIp, String rejectionReason) {
        // 승락 생성사
        this.checkRequest = checkRequest;
        this.processorMember = processor;
        this.processorIp = processorIp;
        this.rejectionReason = rejectionReason;
        approvalStatus = ApprovalStatus.REJECTED;
    }

    public static CheckResult accept(CheckRequest checkRequest, Member processor, String processorIp) {
        return new CheckResult(checkRequest, processor, processorIp);
    }

    public static CheckResult reject(CheckRequest checkRequest, Member processor, String processorIp, String rejectionReason) {
        return new CheckResult(checkRequest, processor, processorIp, rejectionReason);
    }
}