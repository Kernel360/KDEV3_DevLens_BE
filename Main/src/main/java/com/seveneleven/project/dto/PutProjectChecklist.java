package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Checklist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PutProjectChecklist {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String title;
        private String description;

        @Override
        public String toString() {
            return "Request{" +
                    "description='" + description + '\'' +
                    '}';
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private String checklistStatus;

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    '}';
        }

        public Response(Checklist checklist) {
            this.id = checklist.getId();
            this.title = checklist.getTitle();
            this.description = checklist.getDescription();
            this.checklistStatus = checklist.getChecklistStatus().name();
        }

        public static PutProjectChecklist.Response toDto(Checklist checklist) {
            return new PutProjectChecklist.Response(checklist);
        }
    }
}
