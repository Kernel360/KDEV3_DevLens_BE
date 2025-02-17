package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.entity.project.ProjectTag;
import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.PatchProjectCurrentStep;
import com.seveneleven.project.service.authorization.AuthorizationReader;
import com.seveneleven.project.service.project.ProjectReader;
import com.seveneleven.project.service.project.ProjectStore;
import com.seveneleven.project.service.step.StepReader;
import com.seveneleven.project.service.tags.ProjectTagReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectReader projectReader;
    private final ProjectStore projectStore;
    private final ProjectTagReader projectTagReader;
    private final StepReader stepReader;
    private final AuthorizationReader authorizationReader;

    @Override
    public Project getProject(Long projectId) {
        return projectReader.read(projectId);
    }

    @Override
    public List<Project> getMyProjectList(Long memberId, String filter) {
        return projectReader.getMyProjectList(memberId, filter);
    }

    @Override
    public List<Project> getCompanyProject(Long companyId, String filter) {
        return projectReader.getCompanyProject(companyId, filter);
    }

    @Override
    public GetProjectDetail.Response getProjectDetail(Long projectId) {
        Project project = projectReader.read(projectId);

        List<ProjectStep> projectSteps = stepReader.getProjectStep(project.getId());
        List<ProjectTag> projectTags = projectTagReader.getAllByProjectId(projectId);
        List<ProjectAuthorization> authorizations = authorizationReader.readByProjectId(projectId);

        return GetProjectDetail.Response.toDto(project, projectSteps, projectTags, authorizations);
    }

    @Override
    @Transactional
    public PatchProjectCurrentStep.Response patchProjectCurrentStep(Long projectId, Long stepId) {
        Project project = projectReader.read(projectId);
        ProjectStep projectStep = stepReader.read(stepId);

        return PatchProjectCurrentStep.Response.toDto(
                projectStore.modifyProjectCurrentStep(project, projectStep)
        );
    }

    @Override
    public List<String> getProjectTags(Long projectId) {
        return projectTagReader.getAllByProjectId(projectId)
                .stream().map(ProjectTag::getTag).toList();
    }
}
