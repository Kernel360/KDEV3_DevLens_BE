package com.seveneleven.project.dto;

import com.seveneleven.entity.project.CheckResult;
import com.seveneleven.entity.project.constant.ApprovalStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostProjectChecklistAccept {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long checklistId;
        private ApprovalStatus checklistStatus;

        private Response(CheckResult checkResult) {
            checklistId = checkResult.getId();
            checklistStatus = checkResult.getApprovalStatus();
        }

        public static Response toDto(CheckResult checkResult) {
            return new Response(checkResult);
        }
    }
}
