package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.global.entity.YesNo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostProjectChecklistAccept {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long projectId;
        private Long checklistId;
        private YesNo checklistStatus;
        private Double checkRate;
    }
}
