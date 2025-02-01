package com.seveneleven.dto;

import com.seveneleven.entity.project.ProjectHistory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetProjectHistory {
    @Getter
    public static class Response {
        private Long id;
        private String projectName;
        private String customerCompanyName;
        private String developerCompanyName;
        private String description;
        private String statusCode;
        private String typeName;
        private String bnsManager;
        private String contractNumber;
        private LocalDate plannedStartDate;
        private LocalDate startDate;
        private LocalDate plannedEndDate;
        private LocalDate endDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;

        private Response(
                ProjectHistory projectHistory,
                String createdBy,
                String updatedBy
        ) {
            id = projectHistory.getId();
            projectName = projectHistory.getProjectName();
            customerCompanyName = projectHistory.getCustomerName();
            developerCompanyName = projectHistory.getDeveloperName();
            description = projectHistory.getDescription();
            statusCode = projectHistory.getStatusCode();
            typeName = projectHistory.getTypeName();
            bnsManager = projectHistory.getBnsManager();
            contractNumber = projectHistory.getContractNumber();
            plannedStartDate = projectHistory.getPlannedStartDate();
            startDate = projectHistory.getStartDate();
            plannedEndDate = projectHistory.getPlannedEndDate();
            endDate = projectHistory.getEndDate();
            createdAt = projectHistory.getCreatedAt();
            updatedAt = projectHistory.getUpdatedAt();
            this.createdBy = createdBy;
            this.updatedBy = updatedBy;
        }

        public Response(
                ProjectHistory projectHistory
        ) {
            id = projectHistory.getId();
            projectName = projectHistory.getProjectName();
            customerCompanyName = projectHistory.getCustomerName();
            developerCompanyName = projectHistory.getDeveloperName();
            description = projectHistory.getDescription();
            statusCode = projectHistory.getStatusCode();
            typeName = projectHistory.getTypeName();
            bnsManager = projectHistory.getBnsManager();
            contractNumber = projectHistory.getContractNumber();
            plannedStartDate = projectHistory.getPlannedStartDate();
            startDate = projectHistory.getStartDate();
            plannedEndDate = projectHistory.getPlannedEndDate();
            endDate = projectHistory.getEndDate();
            createdAt = projectHistory.getCreatedAt();
            updatedAt = projectHistory.getUpdatedAt();
            createdBy = null;
            updatedBy = null;
        }

        public static Response from(ProjectHistory projectHistory) {
            return new Response(projectHistory);
        }
        public static Response of(
                ProjectHistory project,
                String createdBy,
                String updatedBy
        ){
            return new Response(
                    project,
                    createdBy,
                    updatedBy
            );
        }
    }
}
