package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.entity.project.ProjectTag;
import com.seveneleven.entity.project.constant.MemberType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class GetProjectDetail {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String projectTypeName;
        private String projectName;
        private String projectDescription;
        private String bnsManager;
        private List<String> projectTags;
        private List<ProjectSteps> projectSteps;
        private String customerCompanyName;
        private List<CustomerMemberAuthorization> customerMemberAuthorizations;
        private String developerCompanyName;
        private List<DeveloperMemberAuthorization> developerMemberAuthorizations;

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    '}';
        }

        private Response(
                Project project,
                List<ProjectStep> projectSteps,
                List<ProjectTag> projectTags,
                List<ProjectAuthorization> authorizations
        ) {
            this.id = project.getId();
            this.projectTypeName = project.getProjectType().getProjectTypeName();
            this.projectName = project.getProjectName();
            this.projectDescription = project.getProjectDescription();
            this.bnsManager = project.getBnsManager();
            this.projectTags = projectTags.stream().map(ProjectTag::getTag).toList();
            this.projectSteps = projectSteps.stream().map(step -> ProjectSteps.toDto(project, step)).toList();
            this.customerCompanyName = project.getCustomer().getCompanyName();
            this.developerCompanyName = project.getDeveloper().getCompanyName();
            this.customerMemberAuthorizations = new ArrayList<>();
            this.developerMemberAuthorizations = new ArrayList<>();
            authorizations.forEach(authorization -> {
                if(MemberType.CLIENT.equals(authorization.getMemberType())) {
                    customerMemberAuthorizations.add(new CustomerMemberAuthorization(authorization));
                } else {
                    developerMemberAuthorizations.add(new DeveloperMemberAuthorization(authorization));
                }
            });
        }

        public static Response toDto(
                Project project,
                List<ProjectStep> projectSteps,
                List<ProjectTag> projectTags,
                List<ProjectAuthorization> authorizations
        ) {
            return new Response(project, projectSteps, projectTags, authorizations);
        }
    }

    @Getter
    public static class ProjectSteps {
        private Long stepId;
        private String stepName;
        private Boolean isCurrentStep;

        private ProjectSteps(Project project, ProjectStep projectStep) {
            this.stepId = projectStep.getId();
            this.stepName = projectStep.getStepName();
            this.isCurrentStep = projectStep.getStepName().equals(project.getCurrentProjectStep());
        }

        public static ProjectSteps toDto(Project project, ProjectStep projectStep) {
            return new ProjectSteps(project, projectStep);
        }
    }

    @Getter
    public static class CustomerMemberAuthorization {
        private Long memberId;
        private String memberName;
        private String department;
        private String position;
        private String projectAuthorization;

        @Override
        public String toString() {
            return "CustomerMemberAuthorization{" +
                    "memberId=" + memberId +
                    '}';
        }

        private CustomerMemberAuthorization(ProjectAuthorization projectAuthorization) {
            this.memberId = projectAuthorization.getMember().getId();
            this.memberName = projectAuthorization.getMember().getName();
            this.department = projectAuthorization.getMember().getDepartment();
            this.position = projectAuthorization.getMember().getPosition();
            this.projectAuthorization = projectAuthorization.getAuthorizationCode();
        }

        public static CustomerMemberAuthorization toDto(ProjectAuthorization projectAuthorization) {
            return new CustomerMemberAuthorization(projectAuthorization);
        }
    }

    @Getter
    public static class DeveloperMemberAuthorization {
        private Long memberId;
        private String memberName;
        private String department;
        private String position;
        private String projectAuthorization;

        @Override
        public String toString() {
            return "DeveloperMemberAuthorization{" +
                    "memberId=" + memberId +
                    '}';
        }

        private DeveloperMemberAuthorization(ProjectAuthorization projectAuthorization) {
            this.memberId = projectAuthorization.getMember().getId();
            this.memberName = projectAuthorization.getMember().getName();
            this.department = projectAuthorization.getMember().getDepartment();
            this.position = projectAuthorization.getMember().getPosition();
            this.projectAuthorization = projectAuthorization.getAuthorizationCode();
        }

        public static DeveloperMemberAuthorization toDto(ProjectAuthorization projectAuthorization) {
            return new DeveloperMemberAuthorization(projectAuthorization);
        }
    }
}
