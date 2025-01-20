package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetProjectStep;
import com.seveneleven.project.dto.GetStepChecklist;
import com.seveneleven.project.dto.PostProjectStep;
import com.seveneleven.project.service.ProjectStepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectStepFacade {

    private final ProjectStepService projectStepService;

    public GetProjectStep.Response getProjectStepAndChecklist(Long projectId) {
        return projectStepService.getProjectStep(projectId);
    }

    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return projectStepService.getStepChecklist(stepId);
    }

    public PostProjectStep.Response postProjectStep(PostProjectStep.Request requestDto) {
        return projectStepService.postProjectStep(requestDto);
    }
}
