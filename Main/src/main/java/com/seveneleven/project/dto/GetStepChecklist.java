package com.seveneleven.project.dto;

import com.seveneleven.entity.global.YesNo;
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
    public static class Response {
        private Long stepId;
        // List -> PageResponse로 변경 필요
        private List<ProjectChecklist> checklists;

        private Response(Long stepId, List<ProjectChecklist> checklists) {
            this.stepId = stepId;
            this.checklists = checklists;
        }

        public static Response toDto(Long stepId, List<ProjectChecklist> checklists) {
            return new Response(stepId, checklists);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ProjectChecklist {
        private Long checklistId;
        private String checklistName;
        private Boolean checklistStatus;
        private LocalDateTime checkAcceptTime;

        public ProjectChecklist(Long checklistId, String checklistName, YesNo checklistStatus, LocalDateTime checkAcceptTime) {
            this.checklistId = checklistId;
            this.checklistName = checklistName;
            this.checklistStatus = checklistStatus == YesNo.YES;
            this.checkAcceptTime = checkAcceptTime;
        }
    }
}
