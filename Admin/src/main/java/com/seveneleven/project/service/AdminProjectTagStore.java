package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectTag;

import java.util.List;

public interface AdminProjectTagStore {
    List<ProjectTag> store(Project project, List<String> tags, List<ProjectTag> projectTags);
}
