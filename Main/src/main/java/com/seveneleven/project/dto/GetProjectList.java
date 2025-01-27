package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class GetProjectList {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    public static class Response {
        List<GetMyProjectResponseInfo> myProjects;
        List<GetCompanyProjectResponseInfo> companyProjects;

        private Response(List<GetMyProjectResponseInfo> myProjects, List<GetCompanyProjectResponseInfo> companyProjects) {
            this.myProjects = myProjects;
            this.companyProjects = companyProjects;
        }

        public static GetProjectList.Response toDto(List<GetMyProjectResponseInfo> myProjects,
                                                    List<GetCompanyProjectResponseInfo> companyProjects) {
            return new GetProjectList.Response(myProjects, companyProjects);
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
        private String customerName;

        private GetMyProjectResponseInfo(Project project) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.startDate = project.getStartDate();
            this.endDate = project.getEndDate();
            this.customerId = project.getCustomer().getId();
            this.customerName = project.getCustomer().getCompanyName();
        }

        public static List<GetMyProjectResponseInfo> toDto(List<Project> project) {
            return project.stream()
                    .map(GetMyProjectResponseInfo::new)
                    .toList();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCompanyProjectResponseInfo {
        private Long id;
        private String projectName;
        private LocalDate startDate;
        private LocalDate endDate;
        private Long customerId;
        private String customerName;

        private GetCompanyProjectResponseInfo(Project project) {
            this.id = project.getId();
            this.projectName = project.getProjectName();
            this.startDate = project.getStartDate();
            this.endDate = project.getEndDate();
            this.customerId = project.getCustomer().getId();
            this.customerName = project.getCustomer().getCompanyName();
        }

        public static List<GetCompanyProjectResponseInfo> toDto(List<Project> project) {
            return project.stream()
                    .map(GetCompanyProjectResponseInfo::new)
                    .toList();
        }
    }
}
