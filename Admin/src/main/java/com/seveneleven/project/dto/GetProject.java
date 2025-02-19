package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.ProjectTag;
import com.seveneleven.entity.project.constant.MemberType;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetProject {
    @Getter
    public static class Response {
        private Long id; // 프로젝트 ID
        private String projectName; // 프로젝트명
        private Long customerCompanyId;
        private String customerCompanyName; // 고객사 이름
        private Long developerCompanyId;
        private String developerCompanyName; // 개발사 이름
        private String projectDescription; // 프로젝트 설명
        private Long projectTypeId; // 프로젝트 유형 ID
        private ProjectStatusCode projectStatusCode; // 프로젝트 상태 코드
        private String bnsManager; // BNS 담당자 ID (Member 엔티티의 ID)
        private String contractNumber; // 계약서 번호
        private LocalDate plannedStartDate; // 시작 예정일
        private LocalDate plannedEndDate; // 종료 예정일
        private LocalDate startDate; // 시작일
        private LocalDate endDate; // 종료일
        private String finalApprover; // 최종 결재자
        private LocalDateTime finalApprovalDate; // 최종 결재일시
        private List<String> projectTags;
        private List<PatchAuthorization.CustomerMemberAuthorization> customerMemberAuthorizations;
        private List<PatchAuthorization.DeveloperMemberAuthorization> developerMemberAuthorizations;

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    '}';
        }

        private Response(
                Project project,
                List<ProjectTag> tags,
                List<ProjectAuthorization> authorizations
        ) {
            id = project.getId();
            projectName = project.getProjectName();
            customerCompanyId = project.getCustomer().getId();
            customerCompanyName = project.getCustomer().getCompanyName();
            developerCompanyId = project.getDeveloper().getId();
            developerCompanyName = project.getDeveloper().getCompanyName();
            projectDescription = project.getProjectDescription();
            projectTypeId = project.getProjectType().getId();
            projectStatusCode = project.getProjectStatusCode();
            bnsManager = project.getBnsManager();
            contractNumber = project.getContractNumber();
            plannedStartDate = project.getPlannedStartDate();
            plannedEndDate = project.getPlannedEndDate();
            startDate = project.getStartDate();
            endDate = project.getEndDate();
            finalApprovalDate = project.getFinalApprovalDate();
            projectTags = tags.stream().map(ProjectTag::getTag).toList();
            this.customerMemberAuthorizations = new ArrayList<>();
            this.developerMemberAuthorizations = new ArrayList<>();
            authorizations.forEach(authorization -> {
                if(MemberType.CLIENT.equals(authorization.getMemberType())) {
                    customerMemberAuthorizations.add(new PatchAuthorization.CustomerMemberAuthorization(authorization));
                } else {
                    developerMemberAuthorizations.add(new PatchAuthorization.DeveloperMemberAuthorization(authorization));
                }
            });
        }

        public static Response of(Project project, List<ProjectTag> tags, List<ProjectAuthorization> authorizations) {
            return new Response(project, tags, authorizations);
        }
    }
}
