package com.seveneleven.project.service;

import com.seveneleven.entity.project.ProjectTag;

import java.util.List;

public interface ProjectTagReader {
    List<ProjectTag> readByProjectId(Long projectId);
}
