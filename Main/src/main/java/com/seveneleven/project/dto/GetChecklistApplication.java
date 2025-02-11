package com.seveneleven.project.dto;

import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class GetChecklistApplication {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private List<ChecklistApplication> applications;

        private Response(List<ChecklistApplication> applications) {
            this.applications = applications;
        }

        public static Response toDto(List<ChecklistApplication> applications) {
            return new Response(applications);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ChecklistApplication {
        private Long applicationId;
        private String checklistTitle;
        private String applicationTitle;
        private String applicationContent;
        private String processStatus;
        private String proposer;
        private LocalDateTime applicationDate;
        private ApplicationResult applicationResult;

        private ChecklistApplication(CheckRequest checkRequest, CheckResult checkResult) {
            this.applicationId = checkRequest.getId();
            this.checklistTitle = checkRequest.getChecklist().getTitle();
            this.applicationTitle = checkRequest.getTitle();
            this.applicationContent = checkRequest.getContent();
            this.processStatus = checkRequest.getApprovalStatus().getDescription();
            this.proposer = checkRequest.getRequester().getName();
            this.applicationDate = checkRequest.getCreatedAt();
            this.applicationResult = ApplicationResult.toDto(checkResult);
        }

        private ChecklistApplication(CheckRequest checkRequest) {
            this.applicationId = checkRequest.getId();
            this.checklistTitle = checkRequest.getChecklist().getTitle();
            this.applicationTitle = checkRequest.getTitle();
            this.applicationContent = checkRequest.getContent();
            this.processStatus = checkRequest.getApprovalStatus().getDescription();
            this.proposer = checkRequest.getRequester().getName();
            this.applicationDate = checkRequest.getCreatedAt();
        }

        public static ChecklistApplication toDto(CheckRequest checkRequest, CheckResult checkResult) {
            return new ChecklistApplication(checkRequest, checkResult);
        }

        public static ChecklistApplication toDto(CheckRequest checkRequest) {
            return new ChecklistApplication(checkRequest);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ApplicationResult {
        private String rejectReason;
        private String processor;
        private LocalDateTime processDate;

        public ApplicationResult(CheckResult checkResult) {
            this.rejectReason = checkResult.getRejectionReason();
            this.processor = checkResult.getProcessorMember().getName();
            this.processDate = checkResult.getCreatedAt();
        }

        public static ApplicationResult toDto(CheckResult checkResult) {
            return new ApplicationResult(checkResult);
        }
    }
}
