package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.project.constant.ChecklistStatus;
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
@Table(name = "checklist")
public class Checklist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 체크리스트 ID

    @JoinColumn(name = "project_step_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectStep projectStep; // 프로젝트 단계 ID

    @Column(nullable = false)
    private String title; // 체크리스트 제목

    @Column(columnDefinition = "TEXT")
    private String description; // 체크리스트 설명

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChecklistStatus checklistStatus; // 사용 유무

    private Long approverId; // 승인자 ID

    private LocalDateTime approvalDate; // 승인 일시

    private Checklist(String title, String description, ProjectStep projectStep) {
        this.projectStep = projectStep;
        this.title = title;
        this.description = description;
        this.checklistStatus = ChecklistStatus.APPROVE_WAITING;
        this.approverId = null;
        this.approvalDate = null;
    }

    public static Checklist create(String title, String description, ProjectStep projectStep) {
        return new Checklist(title, description, projectStep);
    }

    public Checklist updateChecklist(String title, String description) {
        if(checklistStatus != ChecklistStatus.DELETED) {
            this.title = title;
            this.description = description;
            return this;
        }
        throw new BusinessException(ErrorCode.CHECKLIST_ALREADY_DELETED);
    }

//    public Checklist(ProjectStep projectStep, String title, String description, YesNo isActive, YesNo isChecked, Long approverId, LocalDateTime approvalDate) {
//        this.projectStep = projectStep;
//        this.title = title;
//        this.description = description;
//        this.isActive = isActive;
//        this.isChecked = isChecked;
//        this.approverId = approverId;
//        this.approvalDate = approvalDate;
//    }

    public Checklist deleteChecklist() {
        if(checklistStatus != ChecklistStatus.DELETED) {
            checklistStatus = ChecklistStatus.DELETED;
            return this;
        }
        throw new BusinessException(ErrorCode.CHECKLIST_ALREADY_DELETED);
    }

    public void acceptChecklist() {
        if(checklistStatus == ChecklistStatus.APPROVE_WAITING) {
            checklistStatus = ChecklistStatus.APPROVED;
            return;
        }
        throw new BusinessException(ErrorCode.CHECKLIST_ALREADY_CHECKED);
    }
}