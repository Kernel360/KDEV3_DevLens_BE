package com.seveneleven.project.dto;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectStep;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class GetProjectStep {
    /**
     * AllArgsContructor는 개발 시 삭제 예정
     */
    @Getter
    @NoArgsConstructor
    public static class Response {
        private Long projectId;
        private List<ProjectStepInfo> projectStepInfo;

        public Response(Long projectId, List<ProjectStepInfo> projectStepInfo) {
            this.projectId = projectId;
            this.projectStepInfo = projectStepInfo;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ProjectStepInfo {
        private Long stepId;
        private String stepName;
        private Integer stepOrder;
        private List<ProjectChecklist> projectChecklist;

        private ProjectStepInfo(ProjectStep projectStep, List<ProjectChecklist> projectChecklist) {
            stepId = projectStep.getId();
            stepName = projectStep.getStepName();
            stepOrder = projectStep.getStepOrder();
            this.projectChecklist = projectChecklist;
        }

        public static ProjectStepInfo toDto(ProjectStep projectStep, List<ProjectChecklist> projectChecklist) {
            return new ProjectStepInfo(projectStep, projectChecklist);
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ProjectChecklist {
        private Long checklistId;
        private String checklistName;
        private YesNo checklistStatus;
        private LocalDateTime approvalTime;

        public ProjectChecklist(Long checklistId, String checklistName, YesNo checklistStatus, LocalDateTime approvalTime) {
            this.checklistId = checklistId;
            this.checklistName = checklistName;
            this.checklistStatus = checklistStatus;
            this.approvalTime = approvalTime;
        }
    }
}
