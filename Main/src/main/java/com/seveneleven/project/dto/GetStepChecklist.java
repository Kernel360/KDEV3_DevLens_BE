package com.seveneleven.project.dto;

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

        @Override
        public String toString() {
            return "Response{" +
                    "stepId=" + stepId +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor
    public static class projectChecklist {
        private Long checklistId;
        private String checklistName;
        private String checklistStatus;
        private LocalDateTime checkAcceptTime;

        @Override
        public String toString() {
            return "projectChecklist{" +
                    "checklistId=" + checklistId +
                    '}';
        }

        public projectChecklist(Long checklistId, String checklistName, String checklistStatus, LocalDateTime checkAcceptTime) {
            this.checklistId = checklistId;
            this.checklistName = checklistName;
            this.checklistStatus = checklistStatus;
            this.checkAcceptTime = checkAcceptTime;
        }
    }
}
