package com.seveneleven.devlens.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class GetProjectDetail {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long projectId;
        private String projectType;
        private String projectName;
        private ProjectStepInfo projectStep;
        private String projectDescription;
        private String projectContact;
        private String projectContactPhone;
        private String projectImageURL;
        private Page<ChecklistApplicationList> checklistApplicationList;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectStepInfo {
        private Long stepId;
        private String stepName;
        private Double stepProcessRate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChecklistApplicationList {
        private Long checklistApplicationId;
        private String StepName;
        private String checklistName;
        private String applicationTitle;
        private String applicationUserName;
        private LocalDateTime ApplicationDateTime;
    }
}
