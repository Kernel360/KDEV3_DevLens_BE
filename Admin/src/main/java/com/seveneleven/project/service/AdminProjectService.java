package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.GetProjectList;
import com.seveneleven.project.dto.PostProject;
import com.seveneleven.project.dto.PutProject;
import com.seveneleven.response.PaginatedResponse;

import java.util.List;

public interface AdminProjectService {
    Project createProject(PostProject.Request request);
    Project getProject(Long id);
    PaginatedResponse<GetProjectList.Response> getListOfProject(Integer page);
    Project updateProject(Long id, PutProject.Request request);
    Project deleteProject(Long id);
    String checkProjectNameExists(String projectName);
    PaginatedResponse<GetProjectList.Response> getCompanyProject(Integer page, Long id);
    List<Project> getProcessingProjects();
}
