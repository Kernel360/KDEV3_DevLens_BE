package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.project.dto.PatchAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProjectAuthorizationServiceImpl implements AdminProjectAuthorizationService {

    private final AdminProjectAuthorizationStore authorizationStore;
    private final AdminProjectAuthorizationReader authorizationReader;

    @Override
    public List<ProjectAuthorization> readByProjectId(Long projectId) {
        return authorizationReader.getAllByProjectId(projectId);
    }

    @Override
    public List<ProjectAuthorization> store(
            Project project,
            List<PatchAuthorization.CustomerMemberAuthorization> customers,
            List<PatchAuthorization.DeveloperMemberAuthorization> developers
    ) {
        List<ProjectAuthorization> authorizations = authorizationReader.getAllByProjectId(project.getId());
        return authorizationStore.store(customers, developers, authorizations, project);
    }
}
