package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.project.constant.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "check_request_history")
public class CheckRequestHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 체크 요청 ID

    @Column(nullable = false)
    private Long checklistId; // 체크 리스트 ID

    @Column(nullable = false)
    private LocalDateTime requestDate; // 체크 요청 일시

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus; // 승인 여부(결과)

    private String requesterName; // 요청자 이름

    private String requestIp; // 요청자 IP

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content; // 요청 내용

    public CheckRequestHistory(CheckRequest checkRequest) {
        this.checklistId = checkRequest.getChecklist().getId();
        this.requestDate = checkRequest.getRequestDate();
        this.approvalStatus = checkRequest.getApprovalStatus();
        this.requesterName = checkRequest.getRequester().getName();
        this.requestIp = checkRequest.getRequestIp();
        this.title = checkRequest.getTitle();
        this.content = checkRequest.getContent();
    }
}