package com.seveneleven.project.service.tags;

import com.seveneleven.entity.project.ProjectTag;

import java.util.List;

public interface ProjectTagReader {
    List<ProjectTag> getAllByProjectId(Long projectId);
}
