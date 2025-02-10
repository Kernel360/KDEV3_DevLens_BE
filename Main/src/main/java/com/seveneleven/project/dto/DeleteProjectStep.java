package com.seveneleven.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class DeleteProjectStep {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long projectId;
        private Long stepId;

        @Override
        public String toString() {
            return "Response{" +
                    "projectId=" + projectId +
                    '}';
        }

        private Response(Long projectId, Long stepId) {
            this.projectId = projectId;
            this.stepId = stepId;
        }

        public static Response toDto(Long projectId, Long stepId) {
            return new Response(projectId, stepId);
        }
    }
}
