package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.project.dto.PostProject;

import java.util.List;

public interface AdminProjectAuthorizationStore {
    List<ProjectAuthorization> store(
            PostProject.Request requestDto,
            List<ProjectAuthorization> existingProjectAuthorizations,
            Project project
    );
}
