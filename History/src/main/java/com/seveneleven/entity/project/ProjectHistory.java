package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_history")
public class ProjectHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 관리 번호

    @Column(nullable = false)
    private String projectName; // 프로젝트명

    @Column(nullable = false)
    private String customerName; // 고객사 이름

    @Column(nullable = false, length = 255)
    private String developerName; // 개발사 이름

    @Column(columnDefinition = "TEXT")
    private String description; // 프로젝트 설명

    @Column(nullable = false)
    private String statusCode; // 프로젝트 상태 코드 (준비, 진행, 완료, 보류, 취소)

    @Column(nullable = false)
    private String typeName; // 프로젝트 유형명

    private String bnsManager; // BNS 담당자 ID

    private String contractNumber; // 계약서 번호

    private LocalDate plannedStartDate; // 시작 예정일

    private LocalDate startDate; // 시작일

    private LocalDate plannedEndDate; // 종료 예정일

    private LocalDate endDate; // 종료일

    private ProjectHistory(Project project) {
        this.projectName = project.getProjectName();
        this.customerName = project.getCustomer().getCompanyName();
        this.developerName = project.getDeveloper().getCompanyName();
        this.description = project.getProjectDescription();
        this.statusCode = project.getProjectStatusCode().name();
        this.typeName = project.getProjectType().getProjectTypeName();
        this.bnsManager = project.getBnsManager();
        this.contractNumber = project.getContractNumber();
        this.plannedStartDate = project.getPlannedStartDate();
        this.startDate = project.getStartDate();
        this.plannedEndDate = project.getPlannedEndDate();
        this.endDate = project.getEndDate();
    }

    public static ProjectHistory create(Project project) {
        return new ProjectHistory(project);
    }
}