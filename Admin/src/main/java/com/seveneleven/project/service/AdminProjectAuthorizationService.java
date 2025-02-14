package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.project.dto.PostProject;

import java.util.List;

public interface AdminProjectAuthorizationService {
    List<ProjectAuthorization> store(Project project, PostProject.Request authorization);
}
