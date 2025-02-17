package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostProjectChecklist {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    public static class Request {
        private String checklistTitle;
        private String checklistDescription;

        public Request(String checklistTitle, String checklistDescription) {
            this.checklistTitle = checklistTitle;
            this.checklistDescription = checklistDescription;
        }

        @Override
        public String toString() {
            return "Request{" +
                    "checklistTitle='" + checklistTitle + '\'' +
                    '}';
        }

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
        private String checklistStatus;

        public Response(Checklist checklist) {
            projectChecklistId = checklist.getId();
            checklistTitle = checklist.getTitle();
            checklistDescription = checklist.getDescription();
            checklistStatus = checklist.getChecklistStatus().name();
        }

        @Override
        public String toString() {
            return "Response{" +
                    "projectChecklistId=" + projectChecklistId +
                    '}';
        }

        public static Response toDto(Checklist checklist) {
            return new Response(checklist);
        }
    }
}
