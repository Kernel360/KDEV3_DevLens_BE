package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.global.converter.YesNoConverter;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.constant.ApprovalStatus;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "check_request")
public class CheckRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 체크 요청 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Checklist checklist; // 체크 리스트 ID

    @Column(nullable = false)
    private LocalDateTime requestDate; // 체크 요청 일시

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus; // 승인 여부(결과)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Member requester; // 요청자 ID

    private String requestIp; // 요청자 IP

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content; // 요청 내용

    @Column(nullable = false)
    @Convert(converter = YesNoConverter.class)
    private YesNo isActive; // 사용 유무

    private CheckRequest(String title, String content, Member requester, String requestIp) {
        this.requester = requester;
        this.requestIp = requestIp;
        this.title = title;
        this.content = content;
        this.isActive = YesNo.YES;
    }

    public static CheckRequest create(String title, String content, Member requester, String requestIp) {
        return new CheckRequest(title, content, requester, requestIp);
    }

    public CheckRequestHistory createCheckRequestHistory() {
        return new CheckRequestHistory(this);
    }

    public void accept() {
        if(approvalStatus.equals(ApprovalStatus.WAITING)) {
            approvalStatus = ApprovalStatus.APPROVED;
        }
        throw new BusinessException(ErrorCode.CHECK_REQUEST_ALREADY_HAS_RESULT);
    }

    public void reject() {
        if(approvalStatus.equals(ApprovalStatus.WAITING)) {
            approvalStatus = ApprovalStatus.REJECTED;
        }
        throw new BusinessException(ErrorCode.CHECK_REQUEST_ALREADY_HAS_RESULT);
    }
}