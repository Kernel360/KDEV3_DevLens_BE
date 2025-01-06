package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.domain.member.constant.YN;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DeleteProjectChecklist {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long checklistId;
        private YN checklistStatus;
    }
}
