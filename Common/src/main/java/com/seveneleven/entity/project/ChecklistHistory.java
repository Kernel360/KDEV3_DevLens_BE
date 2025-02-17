package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
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
@Table(name = "checklist_history")
public class ChecklistHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 체크리스트 ID

    private String projectStepName; // 프로젝트 단계명

    @Column(nullable = false)
    private String title; // 체크리스트 제목

    @Column(columnDefinition = "TEXT")
    private String description; // 체크리스트 설명

    @Column(nullable = false)
    private String checklistStatus;

    private Long approverId; // 승인자 ID

    private LocalDateTime approvalDate; // 승인 일시

    private ChecklistHistory(Checklist checklist) {
        this.id = checklist.getId();
        this.projectStepName = checklist.getProjectStep().getStepName();
        this.title = checklist.getTitle();
        this.description = checklist.getDescription();
        this.checklistStatus = checklist.getChecklistStatus().name();
        this.approverId = checklist.getApproverId();
        this.approvalDate = checklist.getApprovalDate();
    }

    public static ChecklistHistory create(Checklist checklist) {
        if(checklist == null) {
            throw new BusinessException(ErrorCode.CHECKLIST_NOT_FOUND);
        }

        return new ChecklistHistory(checklist);
    }
}
