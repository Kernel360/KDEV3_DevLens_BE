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
    @AllArgsConstructor
    public static class Response {
        private Long projectChecklistId;
        private String checklistTitle;
        private String checklistDescription;
        private YesNo isActive;
        private YesNo isChecked;

        public static Response toDto(Checklist checklist) {
            return new Response(
                    checklist.getId(),
                    checklist.getTitle(),
                    checklist.getDescription(),
                    checklist.getIsActive(),
                    checklist.getIsChecked()
            );
        }
    }
}
