package com.seveneleven.project.service.project;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.GetProjectDetail;

import java.util.List;

public interface ProjectReader {
    Project read(Long projectId);
    List<Project> getMyProjectList(Long memberId, String filter);
    List<Project> getCompanyProject(Long companyId, String filter);
}