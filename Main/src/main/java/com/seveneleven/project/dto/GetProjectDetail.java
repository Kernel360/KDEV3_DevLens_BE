package com.seveneleven.project.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class GetProjectDetail {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @Setter
    public static class Response {
        private ProjectDetail projectDetail;
        private List<ProjectStepInfo> projectStep;
        private List<ChecklistApplicationList> checklistApplicationList;

        private Response(ProjectDetail projectDetail, List<ProjectStepInfo> projectStep, List<ChecklistApplicationList> checklistApplicationList) {
            this.projectDetail = projectDetail;
            this.projectStep = projectStep;
            this.checklistApplicationList = checklistApplicationList;
        }

        public static Response toDto(ProjectDetail projectDetail,
                                     List<ProjectStepInfo> projectStep,
                                     List<ChecklistApplicationList> checklistApplicationList
        ) {
            return new Response(projectDetail, projectStep, checklistApplicationList);
        }
    }

    @Getter
    public static class ProjectDetail {
        private Long id;
        private String projectTypeName;
        private String projectName;
        private String projectDescription;
        private String bnsManagerName;

        public ProjectDetail(
                Long projectId,
                String projectTypeName,
                String projectName,
                String projectDescription,
                String name
        ) {
            this.id = projectId;
            this.projectTypeName = projectTypeName;
            this.projectName = projectName;
            this.projectDescription = projectDescription;
            this.bnsManagerName = name;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectStepInfo {
        private Long stepId;
        private String stepName;
        private Double stepProcessRate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ChecklistApplicationList {
        private Long checklistApplicationId;
        private String StepName;
        private String checklistName;
        private String applicationTitle;
        private String applicationUserName;
        private LocalDateTime ApplicationDateTime;
    }
}
