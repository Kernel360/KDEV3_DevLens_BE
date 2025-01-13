package com.seveneleven.project.dto;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import lombok.Getter;

import java.util.List;

public class PostProjectStep {

    @Getter
    public static class Request {
        Long projectId;
        String stepName;
        String stepDescription;
        Integer stepOrderNumber;
        List<PostChecklist> checklists;

        public ProjectStep toEntity(Project project) {
            return ProjectStep.create(project, stepName, stepDescription, stepOrderNumber);
        }
    }

    public static class PostChecklist {
        String checklistTitle;
        String checklistDescription;

        private PostChecklist(Checklist checklist) {
            this.checklistTitle = checklist.getTitle();
            this.checklistDescription = checklist.getDescription();
        }

        public PostChecklist toDto(Checklist checklist) {
            return new PostChecklist(checklist);
        }

        public Checklist toEntity(ProjectStep projectStep) {
            return Checklist.create(checklistTitle, checklistDescription, projectStep);
        }
    }

    public static class Response {
        Long projectId;
        String stepName;
        Integer stepOrderNumber;
        List<PostChecklist> checklists;

        private Response(ProjectStep projectStep, List<PostChecklist> checklists) {
            this.projectId = projectStep.getProject().getId();
            this.stepName = projectStep.getStepName();
            this.stepOrderNumber = projectStep.getStepOrder();
            this.checklists = checklists;
        }

        public static Response toDto(ProjectStep projectStep, List<PostChecklist> checklists) {
            return new Response(projectStep, checklists);
        }
    }
}
