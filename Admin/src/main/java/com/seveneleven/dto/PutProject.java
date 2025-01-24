package com.seveneleven.dto;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PutProject {
    @Getter
    public static class Request {
        private String projectName; // 프로젝트명
        private Long customerId; // 고객사 ID (Company 엔티티의 ID)
        private Long developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private Long projectTypeId; // 프로젝트 유형 ID
        private Project.ProjectStatusCode projectStatusCode; //
        private String bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일
        private LocalDate startDate; // 시작일
        private LocalDate endDate; // 종료일
        private LocalDateTime finalApprovalDate; // 최종 결재일시

        public Project updateProject(
                Project project,
                Company customer,
                Company developer,
                ProjectType projectType
        ) {
            return project.update(
                    projectName,
                    projectDescription,
                    projectStatusCode,
                    contractNumber,
                    plannedStartDate,
                    plannedEndDate,
                    startDate,
                    endDate,
                    finalApprovalDate,
                    customer,
                    developer,
                    projectType,
                    bnsManager
            );
        }
    }

    @Getter
    public static class Response {
        private Long id;
        private String projectName; // 프로젝트명
        private String customerId; // 고객사 ID (Company 엔티티의 ID)
        private String developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private String projectType; // 프로젝트 유형 ID
        private Project.ProjectStatusCode projectStatusCode; //
        private String bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일
        private LocalDate startDate; // 시작일
        private LocalDate endDate; // 종료일
        private LocalDateTime finalApprovalDate; // 최종 결재일시

        private Response(Project project) {
            id = project.getId();
            projectName = project.getProjectName();
            customerId = project.getCustomer().getCompanyName();
            developerId = project.getDeveloper().getCompanyName();
            projectDescription = project.getProjectDescription();
            projectType = project.getProjectType().getProjectTypeName();
            bnsManager = project.getBnsManager();
            contractNumber = project.getContractNumber();
            plannedStartDate = project.getPlannedStartDate();
            plannedEndDate = project.getPlannedEndDate();
            startDate = project.getStartDate(); // 시작일
            endDate = project.getEndDate(); //종료일
            finalApprovalDate = project.getFinalApprovalDate(); // 최종 결재일시
        }

        public static Response of(Project project) {
            return new Response(project);
        }
    }
}
