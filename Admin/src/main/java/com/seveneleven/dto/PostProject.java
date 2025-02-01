package com.seveneleven.dto;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectType;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostProject {
    @Getter
    public static class Request {
        private String projectName; // 프로젝트명
        private Long customerId; // 고객사 ID (Company 엔티티의 ID)
        private Long developerId; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private Long projectTypeId; // 프로젝트 유형 ID
        private String bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일

        public Project toEntity(
                Company customer,
                Company developer,
                ProjectType projectType
        ) {
            return Project.create(
                    projectName,
                    customer,
                    developer,
                    projectDescription,
                    projectType,
                    ProjectStatusCode.PREPARED,
                    bnsManager,
                    contractNumber,
                    plannedStartDate,
                    plannedEndDate
            );
        }
    }

    @Getter
    public static class Response {
        private Long id; // 프로젝트 ID
        private String projectName; // 프로젝트명
        private String customerCompanyName; // 고객사 ID (Company 엔티티의 ID)
        private String developerCompanyName; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private String projectTypeName; // 프로젝트 유형
        private ProjectStatusCode projectStatusCode; // 프로젝트 상태 코드
        private String bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일

        private Response(
                Project project
        ) {
            id = project.getId();
            projectName = project.getProjectName();
            customerCompanyName = project.getCustomer().getCompanyName();
            developerCompanyName = project.getDeveloper().getCompanyName();
            projectDescription = project.getProjectDescription();
            projectTypeName = project.getProjectType().getProjectTypeName();
            projectStatusCode = project.getProjectStatusCode();
            bnsManager = project.getBnsManager();
            contractNumber = project.getContractNumber();
            plannedStartDate = project.getPlannedStartDate();
            plannedEndDate = project.getPlannedEndDate();
        }

        public static Response of(Project project) {
            return new Response(project);
        }
    }
}
