package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.PatchProjectCurrentStep;

import java.util.List;

public interface ProjectService {
    Project getProject(Long projectId);
    List<Project> getMyProjectList(Long memberId, String filter);
    List<Project> getCompanyProject(Long companyId, String filter);
    GetProjectDetail.Response getProjectDetail(Long projectId);
    PatchProjectCurrentStep.Response patchProjectCurrentStep(Long projectId, Long stepId);
    List<String> getProjectTags(Long projectId);
}
