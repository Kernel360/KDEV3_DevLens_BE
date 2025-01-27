package com.seveneleven.project.dto;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostProjectChecklist {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String checklistTitle;
        private String checklistDescription;

        public Checklist toEntity(ProjectStep projectStep) {
            return Checklist.create(checklistTitle, checklistDescription, projectStep);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long projectChecklistId;
        private String checklistTitle;
        private String checklistDescription;
        private Boolean isActive;
        private Boolean isChecked;

        private Response(Checklist checklist) {
            this.projectChecklistId = checklist.getId();
            this.checklistTitle = checklist.getTitle();
            this.checklistDescription = checklist.getDescription();
            this.isActive = checklist.getIsActive() == YesNo.YES;
            this.isChecked = checklist.getIsChecked() == YesNo.YES;
        }

        public static Response toDto(Checklist checklist) {
            return new Response(checklist);
        }
    }
}
