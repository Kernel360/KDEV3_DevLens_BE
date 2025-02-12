package com.seveneleven.project.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.project.dto.GetMemberAuthorization;
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

    /**
     * 함수명 : getProjectAuthorization
     * 해당 프로젝트 단계에 접근할 수 있는 인원을 반환하는 함수
     */
    @Override
    public GetProjectAuthorization.Response getProjectAuthorization(Project project) {
        List<ProjectAuthorization> authorizations = authorizationReader.readByProjectId(project.getId());
        return GetProjectAuthorization.Response.toDto(project.getId(), authorizations);
    }

    @Override
    public GetMemberAuthorization.Response getMemberAuthorization(Project project, Member member) {
        ProjectAuthorization authorization =
                authorizationReader.readByProjectIdAndUserId(project.getId(), member.getId());

        return GetMemberAuthorization.Response.toDto(authorization);
    }

    /**
     * 함수명 : createProjectAuthorization
     * 해당 프로젝트 단계의 접근 권한을 편집하는 함수
     */
    @Override
    public PostProjectAuthorization.Response createProjectAuthorization(
            Project project,
            PostProjectAuthorization.Request requestDto
    ) {
        List<ProjectAuthorization> authorizations = authorizationReader.readByProjectId(project.getId());
        return authorizationStore.store(requestDto, authorizations, project);
    }
}
