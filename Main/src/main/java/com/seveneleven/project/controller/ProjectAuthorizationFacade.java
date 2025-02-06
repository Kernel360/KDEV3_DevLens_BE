package com.seveneleven.project.controller;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.GetProjectAuthorization;
import com.seveneleven.project.dto.PostProjectAuthorization;
import com.seveneleven.project.service.ProjectAuthorizationService;
import com.seveneleven.project.service.dashboard.ProjectReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectAuthorizationFacade {

    private final ProjectReader projectReader;
    private final ProjectAuthorizationService projectAuthorizationService;

    /**
     * 함수명 : postProjectAuthorization
     * 프로젝트 접근 권한을 편힙하는 함수
     */
    public PostProjectAuthorization.Response postProjectAuthorization(
            PostProjectAuthorization.Request requestDto,
            Long projectId
    ) {
        Project project = projectReader.read(projectId);
        return projectAuthorizationService.createProjectAuthorization(project, requestDto);
    }

    /**
     * 함수명 : getProjectAuthorization
     * 해당 단계에 접근할 수 있는 인원을 반환하는 함수
     */
    public GetProjectAuthorization.Response getProjectAuthorization(Long projectId) {
        Project project = projectReader.read(projectId);
        return projectAuthorizationService.getProjectAuthorization(project);
    }
}
