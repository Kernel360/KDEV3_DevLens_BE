package com.seveneleven.project.dto;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.Checklist;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DeleteProjectChecklist {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long checklistId;
        private Boolean checklistStatus;

        private Response(Checklist checklist) {
            this.checklistId = checklist.getId();
            this.checklistStatus = checklist.getIsActive() == YesNo.YES;
        }

        public static Response toDto(Checklist checklist) {
            return new Response(checklist);
        }
    }
}
