package com.seveneleven.project.controller;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.ProjectAuthorizationService;
import com.seveneleven.project.service.ProjectService;
import com.seveneleven.project.service.ProjectStepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectStepFacade {
    private final ProjectService projectService;
    private final ProjectStepService projectStepService;
    private final ProjectAuthorizationService projectAuthorizationService;

    /**
     * 함수명 : getProjectStepAndChecklist
     * 프로젝트 단계와 체크리스트를 반환하는 함수
     */
    public GetProjectStep.Response getProjectStepAndChecklist(Long projectId) {
        return projectStepService.getProjectStep(projectId);
    }

    /**
     * 함수명 : postProjectStep
     * 프로젝트 단계를 추가하는 함수
     */
    public PostProjectStep.Response postProjectStep(
            Long projectId,
            PostProjectStep.Request requestDto
    ) {
        Project project = projectService.getProject(projectId);
        return projectStepService.postProjectStep(project, requestDto);
    }

    /**
     * 함수명 : putProjectStep
     * 프로젝트 단계를 수정하는 함수
     */
    public PutProjectStep.Response putProjectStep(
            Long stepId,
            PutProjectStep.Request requestDto
    ) {
        ProjectStep projectStep = projectStepService.getProjectStepById(stepId);
        return projectStepService.putProjectStep(projectStep, requestDto);
    }

    /**
     * 함수명 : deleteProjectStep
     * 프로젝트 단계를 삭제하는 함수
     */
    public DeleteProjectStep.Response deleteProjectStep(DeleteProjectStep.Request request) {
        return projectStepService.deleteProjectStep(request.getProjectId(), request.getStepId());
    }

    /**
     * 함수명 : postProjectAuthorization
     * 프로젝트 접근 권한을 편힙하는 함수
     */
    public PostProjectAuthorization.Response postProjectAuthorization(
            PostProjectAuthorization.Request requestDto,
            Long stepId
    ) {
        return projectAuthorizationService.createProjectAuthorization(stepId, requestDto);
    }

    /**
     * 함수명 : getProjectAuthorization
     * 해당 단계에 접근할 수 있는 인원을 반환하는 함수
     */
    public GetProjectAuthorization.Response getProjectAuthorization(Long stepId) {
        return projectAuthorizationService.getProjectAuthorization(stepId);
    }
}
