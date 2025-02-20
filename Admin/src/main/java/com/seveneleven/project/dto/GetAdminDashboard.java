package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectTag;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class GetAdminDashboard {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String projectName;
        private LocalDate startDate;
        private LocalDate endDate;
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
            this.startDate = project.getPlannedStartDate();
            this.endDate = project.getPlannedEndDate();
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
