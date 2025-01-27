package com.seveneleven.project.dto;

import com.seveneleven.entity.global.YesNo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class GetStepChecklist {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long stepId;
        // List -> PageResponse로 변경 필요
        private List<projectChecklist> checklists;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class projectChecklist {
        private Long checklistId;
        private String checklistName;
        private YesNo checklistStatus;
        private LocalDateTime checkAcceptTime;
    }
}
