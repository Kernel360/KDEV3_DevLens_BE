package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class GetCompanyProject {

    @Getter
    @NoArgsConstructor
    public static class Response {
        Long companyId;
        List<CompanyProject> companyProjects;

        @Override
        public String toString() {
            return "Response{" +
                    "companyId=" + companyId +
                    '}';
        }

        private Response(Long companyId, List<CompanyProject> companyProjects) {
            this.companyId = companyId;
            this.companyProjects = companyProjects;
        }

        public static Response toDto(Long companyId, List<CompanyProject> projects) {
            return new Response(companyId, projects);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompanyProject {
        private Long id;
        private String projectName;
        private LocalDate startDate;
        private LocalDate endDate;
        private Long customerId;
        private String customerCompanyName;
        private Long developerId;
        private String developerCompanyName;
        private String currentStepName;
        private List<String> projectTags;

        private CompanyProject(Project project, List<String> projectTags) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.startDate = project.getStartDate();
            this.endDate = project.getEndDate();
            this.customerId = project.getCustomer().getId();
            this.customerCompanyName = project.getCustomer().getCompanyName();
            this.developerId = project.getDeveloper().getId();
            this.developerCompanyName = project.getDeveloper().getCompanyName();
            this.currentStepName = project.getCurrentProjectStep();
            this.projectTags = projectTags;
        }

        public static CompanyProject toDto(Project project, List<String> projectTags) {
            return new CompanyProject(project, projectTags);
        }
    }
}
