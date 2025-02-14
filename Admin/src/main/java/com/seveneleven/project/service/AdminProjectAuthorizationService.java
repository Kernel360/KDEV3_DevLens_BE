package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.project.dto.PatchAuthorization;

import java.util.List;

public interface AdminProjectAuthorizationService {
    List<ProjectAuthorization> readByProjectId(Long projectId);
    List<ProjectAuthorization> store(
            Project project,
            List<PatchAuthorization.CustomerMemberAuthorization> customers,
            List<PatchAuthorization.DeveloperMemberAuthorization> developers
    );
}
