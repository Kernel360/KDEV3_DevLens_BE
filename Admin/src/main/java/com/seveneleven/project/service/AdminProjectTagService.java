package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectTag;

import java.util.List;

public interface AdminProjectTagService {
    List<ProjectTag> storeProjectTags(Project project, List<String> tags);
}
