package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.ProjectChecklistService;
import com.seveneleven.project.service.ProjectStepService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectChecklistFacade {

    private final ProjectStepService projectStepService;
    private final ProjectChecklistService projectChecklistService;

    public GetProjectStep.Response getProjectStepAndChecklist(Long projectId) {
        return projectStepService.getProjectStep(projectId);
    }

    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return projectChecklistService.getStepChecklist(stepId);
    }

    public PostProjectChecklist.Response postProjectChecklist(PostProjectChecklist.Request postProjectChecklist) {
        return projectChecklistService.postProjectChecklist(postProjectChecklist);
    }

    public PutProjectChecklist.Response putProjectChecklist(PutProjectChecklist.Request putProjectChecklist) {
        return projectChecklistService.putProjectChecklist(putProjectChecklist);
    }

    public DeleteProjectChecklist.Response deleteProjectChecklist(Long checklistId) {
        return projectChecklistService.deleteProjectChecklist(checklistId);
    }

    public PostProjectChecklistApplication.Response postProjectChecklistApplication(
            PostProjectChecklistApplication.Request requestDto,
            HttpServletRequest request
    ) {
        return projectChecklistService.postProjectChecklistApplication(requestDto, request);
    }

    public PostProjectChecklistAccept.Response postProjectChecklistAccept(
            Long applicationId,
            Long memberId,
            HttpServletRequest request
    ) {
        return projectChecklistService.postProjectAccept(applicationId, memberId, request);
    }
}
