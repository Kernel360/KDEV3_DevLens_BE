package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.domain.member.constant.YN;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostProjectChecklistAccept {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long projectId;
        private Long checklistId;
        private YN checklistStatus;
        private Double checkRate;

        public Response(Long projectId, Long checklistId, YN checklistStatus, Double checkRate) {
            this.projectId = projectId;
            this.checklistId = checklistId;
            this.checklistStatus = checklistStatus;
            this.checkRate = checkRate;
        }
    }
}
