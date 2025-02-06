package com.seveneleven.project.service.authorization;

import com.seveneleven.entity.project.ProjectAuthorization;

import java.util.List;

public interface AuthorizationReader {
    List<ProjectAuthorization> readByProjectId(Long projectId);
    List<ProjectAuthorization> readByUserId(Long userId);
    ProjectAuthorization readByStepIdAndUserId(Long projectId, Long userId);
}
