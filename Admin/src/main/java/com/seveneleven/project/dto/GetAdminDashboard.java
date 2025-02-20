package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectTag;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import com.seveneleven.util.CustomDateFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class GetAdminDashboard {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String projectName;
        private String startDate;
        private String endDate;
        private Long customerId;
        private String customerCompanyName;
        private Long developerId;
        private String developerCompanyName;
        private String currentStepName;
        private ProjectStatusCode projectStatus;
        private List<String> projectTags;

        private Response(Project project, List<ProjectTag> projectTags) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.startDate = CustomDateFormatter.formatDate(project.getPlannedStartDate());
            this.endDate = CustomDateFormatter.formatDate(project.getPlannedEndDate());
            this.customerId = project.getCustomer().getId();
            this.customerCompanyName = project.getCustomer().getCompanyName();
            this.developerId = project.getDeveloper().getId();
            this.developerCompanyName = project.getDeveloper().getCompanyName();
            this.currentStepName = project.getCurrentProjectStep();
            this.projectTags = projectTags.stream().map(ProjectTag::getTag).toList();
            this.projectStatus = project.getProjectStatusCode();
        }

        public static Response toDto(Project project, List<ProjectTag> projectTags) {
            return new Response(project, projectTags);
        }
    }
}
