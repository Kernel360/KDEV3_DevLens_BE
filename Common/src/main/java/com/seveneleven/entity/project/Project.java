package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.global.converter.YesNoConverter;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
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

    @JoinColumn(name = "bns_manager_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member bnsManager; // BNS 담당자 ID

    @Column(nullable = false)
    @Convert(converter = YesNoConverter.class)
    private YesNo hasImage; // 이미지 여부

    private String contractNumber; // 계약서 번호

    private LocalDate plannedStartDate; // 시작 예정일

    private LocalDate plannedEndDate; // 종료 예정일

    private LocalDate startDate; // 시작일

    private LocalDate endDate; // 종료일

    private Long finalApproverId; // 최종 결재자

    private LocalDateTime finalApprovalDate; // 최종 결재일시
  
    public Project(
            Long id,
            String projectName,
            Company customer,
            Company developer,
            String projectDescription,
            ProjectType projectTypeId,
            ProjectStatusCode projectStatusCode,
            Member bnsManager,
            YesNo hasImage,
            String contractNumber,
            LocalDate plannedStartDate,
            LocalDate plannedEndDate,
            LocalDate startDate,
            LocalDate endDate,
            Long finalApprover,
            LocalDateTime finalApprovalDate
    ) {
        this.id = id;
        this.projectName = projectName;
        this.customer = customer;
        this.developer = developer;
        this.projectDescription = projectDescription;
        this.projectType = projectTypeId;
        this.projectStatusCode = projectStatusCode;
        this.bnsManager = bnsManager;
        this.hasImage = hasImage;
        this.contractNumber = contractNumber;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.finalApproverId = finalApprover;
        this.finalApprovalDate = finalApprovalDate;
    }

    // Dto를 받는 생성자
    public Project(
            String projectName,
            Company customer,
            Company developer,
            String projectDescription,
            ProjectType projectTypeId,
            ProjectStatusCode projectStatusCode,
            Member bnsManager,
            YesNo hasImage,
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
        this.hasImage = hasImage;
        this.contractNumber = contractNumber;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
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
            Long approverId,
            LocalDateTime approvalDate,
            Company customer,
            Company developer,
            ProjectType ProjectType,
            Member bnsManager
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
        this.finalApproverId = approverId;
        this.finalApprovalDate = approvalDate;
        return this;
    }
  
    public enum ProjectStatusCode {
        PREPARED,
        IN_PROGRESS,
        COMPLETED,
        CLOSED,
        CANCELLED
    }
}
