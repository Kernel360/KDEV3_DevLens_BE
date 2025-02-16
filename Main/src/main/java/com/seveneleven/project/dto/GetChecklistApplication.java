package com.seveneleven.project.dto;

import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckResult;
import com.seveneleven.util.file.dto.LinkResponse;
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
        private List<LinkResponse> linkResponse;

        private ChecklistApplication(CheckRequest checkRequest, CheckResult checkResult, List<LinkResponse> linkResponse) {
            this.applicationId = checkRequest.getId();
            this.checklistTitle = checkRequest.getChecklist().getTitle();
            this.applicationTitle = checkRequest.getTitle();
            this.applicationContent = checkRequest.getContent();
            this.processStatus = checkRequest.getApprovalStatus().getDescription();
            this.proposer = checkRequest.getRequester().getName();
            this.applicationDate = checkRequest.getCreatedAt();
            this.applicationResult = ApplicationResult.toDto(checkResult);
            this.linkResponse = linkResponse;
        }

        private ChecklistApplication(CheckRequest checkRequest, List<LinkResponse> linkResponse) {
            this.applicationId = checkRequest.getId();
            this.checklistTitle = checkRequest.getChecklist().getTitle();
            this.applicationTitle = checkRequest.getTitle();
            this.applicationContent = checkRequest.getContent();
            this.processStatus = checkRequest.getApprovalStatus().getDescription();
            this.proposer = checkRequest.getRequester().getName();
            this.applicationDate = checkRequest.getCreatedAt();
            this.linkResponse = linkResponse;
        }

        public static ChecklistApplication toDto(CheckRequest checkRequest, CheckResult checkResult, List<LinkResponse> linkResponse) {
            return new ChecklistApplication(checkRequest, checkResult, linkResponse);
        }

        public static ChecklistApplication toDto(CheckRequest checkRequest, List<LinkResponse> linkResponse) {
            return new ChecklistApplication(checkRequest, linkResponse);
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
