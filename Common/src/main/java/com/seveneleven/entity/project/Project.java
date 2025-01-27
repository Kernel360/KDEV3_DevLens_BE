package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.member.Company;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 프로젝트 ID

    private String projectName; // 프로젝트명

    @JoinColumn(name = "customer_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Company customer; // 고객사 ID

    @JoinColumn(name = "developer_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Company developer; // 개발사 ID

    @Column(columnDefinition = "TEXT")
    private String projectDescription; // 프로젝트 설명

    @JoinColumn(name = "project_type_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectType projectType; // 프로젝트 유형 ID

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectStatusCode projectStatusCode; // 프로젝트 상태 코드 (준비, 진행, 완료, 보류, 취소)

    private String bnsManager; // BNS 담당자

    private String contractNumber; // 계약서 번호

    private LocalDate plannedStartDate; // 시작 예정일

    private LocalDate plannedEndDate; // 종료 예정일

    private LocalDate startDate; // 시작일

    private LocalDate endDate; // 종료일

    private Long finalApproverId; // 최종 결재자

    private LocalDateTime finalApprovalDate; // 최종 결재일시


    // Dto를 받는 생성자
    private Project(
            String projectName,
            Company customer,
            Company developer,
            String projectDescription,
            ProjectType projectTypeId,
            ProjectStatusCode projectStatusCode,
            String bnsManager,
            String contractNumber,
            LocalDate plannedStartDate,
            LocalDate plannedEndDate
    ) {
        this.projectName = projectName;
        this.customer = customer;
        this.developer = developer;
        this.projectDescription = projectDescription;
        this.projectType = projectTypeId;
        this.projectStatusCode = projectStatusCode;
        this.bnsManager = bnsManager;
        this.contractNumber = contractNumber;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
    }

    public static Project create(
            String projectName,
            Company customer,
            Company developer,
            String projectDescription,
            ProjectType projectTypeId,
            ProjectStatusCode projectStatusCode,
            String bnsManager,
            String contractNumber,
            LocalDate plannedStartDate,
            LocalDate plannedEndDate
    ) {
        return new Project(
                projectName,
                customer,
                developer,
                projectDescription,
                projectTypeId,
                projectStatusCode,
                bnsManager,
                contractNumber,
                plannedStartDate,
                plannedEndDate
        );
    }

    public Project update(
            String name,
            String description,
            ProjectStatusCode statusCode,
            String contractNumber,
            LocalDate plannedStartDate,
            LocalDate plannedEndDate,
            LocalDate startDate,
            LocalDate endDate,
            LocalDateTime approvalDate,
            Company customer,
            Company developer,
            ProjectType ProjectType,
            String bnsManager
    ) {
        this.projectName = name;
        this.customer = customer;
        this.developer = developer;
        this.projectDescription = description;
        this.projectType = ProjectType;
        this.projectStatusCode = statusCode;
        this.bnsManager = bnsManager;
        this.contractNumber = contractNumber;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.finalApprovalDate = approvalDate;
        return this;
    }

    public ProjectHistory saveHistory() {
        return ProjectHistory.create(this);
    }

    public Project delete(){
        this.projectStatusCode = ProjectStatusCode.DELETED;
        return this;
    }

    public enum ProjectStatusCode {
        PREPARED,
        IN_PROGRESS,
        COMPLETED,
        CLOSED,
        CANCELLED,
        DELETED
    }
}
