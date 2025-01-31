package com.seveneleven.dto;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetProject {
    @Getter
    public static class Response {
        private Long id; // 프로젝트 ID
        private String projectName; // 프로젝트명
        private String customerName; // 고객사 ID (Company 엔티티의 ID)
        private String developerName; // 개발사 ID (Company 엔티티의 ID)
        private String projectDescription; // 프로젝트 설명
        private String projectTypeName; // 프로젝트 유형 ID
        private ProjectStatusCode projectStatusCode; // 프로젝트 상태 코드
        private String bnsManagerName; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일
        private LocalDate startDate; // 시작일
        private LocalDate endDate; // 종료일
        private String finalApprover; // 최종 결재자
        private LocalDateTime finalApprovalDate; // 최종 결재일시

        private Response(
                Project project
        ) {
            id = project.getId();
            projectName = project.getProjectName();
            customerName = project.getCustomer().getCompanyName();
            developerName = project.getDeveloper().getCompanyName();
            projectDescription = project.getProjectDescription();
            projectTypeName = project.getProjectType().getProjectTypeName();
            projectStatusCode = project.getProjectStatusCode();
            bnsManagerName = project.getBnsManager();
            contractNumber = project.getContractNumber();
            plannedStartDate = project.getPlannedStartDate();
            plannedEndDate = project.getPlannedEndDate();
            startDate = project.getStartDate();
            endDate = project.getEndDate();
            finalApprovalDate = project.getFinalApprovalDate();
        }

        public static Response of(Project project){
            return new Response(project);
        }
    }
}
