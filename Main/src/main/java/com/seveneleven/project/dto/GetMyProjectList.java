package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class GetMyProjectList {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    public static class Response {
        List<GetMyProjectResponseInfo> myProjects;

        @Override
        public String toString() {
            return "Response{" +
                    "myProjects=" + myProjects +
                    '}';
        }

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
        private LocalDate startDate;
        private LocalDate endDate;
        private Long customerId;
        private String customerCompanyName;
        private Long developerId;
        private String developerCompanyName;
        private String currentStepName;
        private List<String> projectTags;

        @Override
        public String toString() {
            return "GetMyProjectResponseInfo{" +
                    "id=" + id +
                    '}';
        }

        private GetMyProjectResponseInfo(Project project, List<String> projectTags) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.startDate = project.getPlannedStartDate();
            this.endDate = project.getPlannedEndDate();
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
