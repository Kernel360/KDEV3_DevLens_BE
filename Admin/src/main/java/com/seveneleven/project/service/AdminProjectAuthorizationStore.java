package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.project.dto.PatchAuthorization;

import java.util.List;

public interface AdminProjectAuthorizationStore {
    List<ProjectAuthorization> store(
            List<PatchAuthorization.CustomerMemberAuthorization> customers,
            List<PatchAuthorization.DeveloperMemberAuthorization> developers,
            List<ProjectAuthorization> existingProjectAuthorizations,
            Project project
    );
}
