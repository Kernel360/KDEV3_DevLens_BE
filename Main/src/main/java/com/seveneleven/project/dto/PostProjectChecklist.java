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

        private Response(Long projectChecklistId, String checklistTitle, String checklistDescription, Boolean isActive, Boolean isChecked) {
            this.projectChecklistId = projectChecklistId;
            this.checklistTitle = checklistTitle;
            this.checklistDescription = checklistDescription;
            this.isActive = isActive;
            this.isChecked = isChecked;
        }

        public static Response toDto(Checklist checklist) {
            return new Response(
                    checklist.getId(),
                    checklist.getTitle(),
                    checklist.getDescription(),
                    checklist.getIsActive() == YesNo.YES,
                    checklist.getIsChecked() == YesNo.YES
            );
        }
    }
}
