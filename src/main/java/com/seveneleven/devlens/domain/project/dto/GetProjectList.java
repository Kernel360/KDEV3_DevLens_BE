package com.seveneleven.devlens.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class GetProjectList {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        List<GetMyProjectResponseInfo> myProjects;
        List<GetCompanyProjectResponseInfo> companyProjects;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetMyProjectResponseInfo {
        private Long projectId;
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private Long customerCompanyId;
        private String customerCompanyName;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetCompanyProjectResponseInfo {
        private Long projectId;
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private Long customerCompanyId;
        private String customerCompanyName;
    }
}
