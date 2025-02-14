package com.seveneleven.project.service.project;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.repository.CheckRequestRepository;
import com.seveneleven.project.repository.ProjectRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectReaderImpl implements ProjectReader {
    private final ProjectRepository projectRepository;

    @Override
    public Project read(Long projectId) {
        return projectRepository.findByIdAndProjectStatusCodeNot(projectId, ProjectStatusCode.DELETED)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));
    }

    @Override
    public List<Project> getMyProjectList(Long memberId, String filter) {
        return projectRepository.findAllProgressingProjects(memberId, filter);
    }

    @Override
    public List<Project> getCompanyProject(Long companyId, String filter) {
        return projectRepository.findAllCompanyProgressingProjects(companyId, filter);
    }
}
