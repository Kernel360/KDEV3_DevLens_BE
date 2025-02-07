package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import com.seveneleven.util.CustomDateFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class GetMyProjectList {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    public static class Response {
        List<GetMyProjectResponseInfo> myProjects;

        private Response(List<GetMyProjectResponseInfo> myProjects) {
            this.myProjects = myProjects;
        }

        public static GetMyProjectList.Response toDto(List<GetMyProjectResponseInfo> myProjects) {
            return new GetMyProjectList.Response(myProjects);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class GetMyProjectResponseInfo {
        private Long id;
        private String projectName;
        private String startDate;
        private String endDate;
        private Long customerId;
        private String customerCompanyName;
        private Long developerId;
        private String developerCompanyName;
        private String currentStepName;
        private List<String> projectTags;

        private GetMyProjectResponseInfo(Project project, List<String> projectTags) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.startDate = CustomDateFormatter.formatDate(project.getStartDate());
            this.endDate = CustomDateFormatter.formatDate(project.getEndDate());
            this.customerId = project.getCustomer().getId();
            this.customerCompanyName = project.getCustomer().getCompanyName();
            this.developerId = project.getDeveloper().getId();
            this.developerCompanyName = project.getDeveloper().getCompanyName();
            this.currentStepName = project.getCurrentProjectStep();
            this.projectTags = projectTags;
        }

        public static GetMyProjectResponseInfo toDto(Project project, List<String> projectTags) {
            return new GetMyProjectResponseInfo(project, projectTags);
        }
    }
}
