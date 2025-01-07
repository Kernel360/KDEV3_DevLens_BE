package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.global.entity.YesNo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PostProjectChecklistReject {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long applicationId;
        private YesNo approvalStatus;
        private Long processorId;
        private String processorIp;
        private LocalDateTime processDate;
        private String rejectReason;
        private YesNo hasFile;
        private YesNo hasLink;
    }
}
