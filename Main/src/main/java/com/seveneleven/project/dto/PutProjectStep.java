package com.seveneleven.project.dto;

import com.seveneleven.entity.project.ProjectStep;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PutProjectStep {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request {
        private String stepName;
        private String stepDescription;
        private Integer stepOrder;
    }

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long stepId;
        private String stepName;
        private String stepDescription;
        private Integer stepOrder;

        private Response(Long stepId, String stepName, String stepDescription, Integer stepOrder) {
            this.stepId = stepId;
            this.stepName = stepName;
            this.stepDescription = stepDescription;
            this.stepOrder = stepOrder;
        }

        public static Response toDto(ProjectStep projectStep) {
            return new Response(projectStep.getId(), projectStep.getStepName(), projectStep.getStepDescription(), projectStep.getStepOrder());
        }
    }
}
