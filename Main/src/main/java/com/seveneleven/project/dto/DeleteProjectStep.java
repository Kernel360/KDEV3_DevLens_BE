package com.seveneleven.project.dto;

import lombok.Getter;

public class DeleteProjectStep {

    @Getter
    public static class Request {
        private Long projectId;
        private Long stepId;
    }

    @Getter
    public static class Response {
        private Long projectId;
        private Long stepId;

        private Response(Long projectId, Long stepId) {
            this.projectId = projectId;
            this.stepId = stepId;
        }

        public static Response toDto(Long projectId, Long stepId) {
            return new Response(projectId, stepId);
        }
    }
}
