package com.seveneleven.project.dto;

import com.seveneleven.entity.project.CheckResult;
import com.seveneleven.entity.project.constant.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostProjectChecklistReject {

    @Getter
    public static class Request {
        private Long applicationId;
        private String rejectReason;
        private List<String> links;
    }

    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long applicationId;
        private ApprovalStatus approvalStatus;
        private Long processorId;
        private String processorIp;
        private LocalDateTime processDate;
        private String rejectReason;

        public Response(CheckResult checkResult) {
            this.applicationId = checkResult.getCheckRequest().getId();
            this.approvalStatus = checkResult.getApprovalStatus();
            this.processorId = checkResult.getProcessorMember().getId();
            this.processorIp = checkResult.getProcessorIp();
            this.processDate = checkResult.getCreatedAt();
            this.rejectReason = checkResult.getRejectionReason();
        }

        public static Response toDto(CheckResult checkResult) {
            return new Response(checkResult);
        }
    }
}
