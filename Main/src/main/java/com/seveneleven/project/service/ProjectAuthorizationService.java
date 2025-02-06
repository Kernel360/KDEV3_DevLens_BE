package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.GetProjectAuthorization;
import com.seveneleven.project.dto.PostProjectAuthorization;

public interface ProjectAuthorizationService {
    GetProjectAuthorization.Response getProjectAuthorization(Project project);
    PostProjectAuthorization.Response createProjectAuthorization(Project project, PostProjectAuthorization.Request requestDto);
}
