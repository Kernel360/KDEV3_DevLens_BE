package com.seveneleven.project.dto;

import com.seveneleven.entity.project.CheckResult;
import com.seveneleven.entity.project.constant.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PostProjectChecklistReject {

    @Getter
    @NoArgsConstructor
    public static class Request {
        private String rejectReason;
//        private List<LinkInput> linkInputs;

        @Override
        public String toString() {
            return "Request{" +
                    "rejectReason='" + rejectReason + '\'' +
                    '}';
        }
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

        @Override
        public String toString() {
            return "Response{" +
                    "applicationId=" + applicationId +
                    '}';
        }

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
