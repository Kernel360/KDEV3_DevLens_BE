package com.seveneleven.project.service;

import com.seveneleven.entity.project.ProjectAuthorization;

import java.util.List;

public interface AdminProjectAuthorizationReader {
    List<ProjectAuthorization> getAllByProjectId(Long projectId);
}
