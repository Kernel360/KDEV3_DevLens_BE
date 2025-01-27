package com.seveneleven.service;

import com.seveneleven.dto.GetProject;
import com.seveneleven.dto.PostProject;
import com.seveneleven.dto.PutProject;
import com.seveneleven.entity.project.Project;
import com.seveneleven.response.PaginatedResponse;

public interface AdminProjectService {
    Project createProject(PostProject.Request request);
    GetProject.Response getProject(Long id);
    PaginatedResponse<GetProject.Response> getListOfProject(Integer page);
    Project updateProject(Long id, PutProject.Request request);
    Project deleteProject(Long id);
    String checkProjectNameExists(String projectName);
    PaginatedResponse<GetProject.Response> getCompanyProject(Integer page, Long id);
}
