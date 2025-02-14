package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.project.dto.PostProject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProjectAuthorizationServiceImpl implements AdminProjectAuthorizationService {

    private final AdminProjectAuthorizationStore authorizationStore;
    private final AdminProjectAuthorizationReader authorizationReader;

    @Override
    public List<ProjectAuthorization> store(Project project, PostProject.Request request) {
        List<ProjectAuthorization> authorizations = authorizationReader.getAllByProjectId(project.getId());
        return authorizationStore.store(request, authorizations, project);
    }
}
