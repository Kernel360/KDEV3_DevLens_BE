package com.seveneleven.devlens.domain.project.dto;

import com.seveneleven.devlens.domain.member.constant.YN;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class GetProjectChecklist {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long projectId;
        // List -> PageResponse로 변경 필요
        private List<projectChecklist> checklists;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class projectChecklist {
        private Long checklistId;
        private String checklistName;
        private YN checklistStatus;
        private LocalDateTime checkAcceptTime;
    }
}
