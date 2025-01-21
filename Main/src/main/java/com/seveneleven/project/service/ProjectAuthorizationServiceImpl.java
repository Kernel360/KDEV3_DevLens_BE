package com.seveneleven.project.service;

import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.GetProjectAuthorization;
import com.seveneleven.project.dto.PostProjectAuthorization;
import com.seveneleven.project.service.authorization.AuthorizationReader;
import com.seveneleven.project.service.authorization.AuthorizationStore;
import com.seveneleven.project.service.step.StepReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectAuthorizationServiceImpl implements ProjectAuthorizationService {

    private final StepReader stepReader;
    private final AuthorizationReader authorizationReader;
    private final AuthorizationStore authorizationStore;

    @Override
    public GetProjectAuthorization.Response getProjectAuthorization(Long stepId) {
        List<ProjectAuthorization> authorizations = authorizationReader.readByStepId(stepId);
        return GetProjectAuthorization.Response.toDto(stepId, authorizations);
    }

    @Override
    public PostProjectAuthorization.Response createProjectAuthorization(
            Long stepId,
            PostProjectAuthorization.Request requestDto
    ) {
        ProjectStep projectStep = stepReader.read(stepId);
        List<ProjectAuthorization> authorizations = authorizationReader.readByStepId(stepId);
        return authorizationStore.store(requestDto, authorizations, projectStep);
    }
}
