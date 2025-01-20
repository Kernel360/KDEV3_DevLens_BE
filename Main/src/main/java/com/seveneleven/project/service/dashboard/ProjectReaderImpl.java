package com.seveneleven.project.service.dashboard;

import com.seveneleven.entity.project.Project;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.GetProjectList;
import com.seveneleven.project.repository.CheckRequestRepository;
import com.seveneleven.project.repository.ProjectRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProjectReaderImpl implements ProjectReader {
    private final ProjectRepository projectRepository;
    private final ProjectStepRepository projectStepRepository;
    private final CheckRequestRepository checkRequestRepository;

    @Override
    @Transactional(readOnly = true)
    public Project read(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(
                        () -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND)
                );
    }

    @Override
    @Transactional(readOnly = true)
    public GetProjectList.Response getProjectList(Long memberId, Long companyId) {
        return new GetProjectList.Response(
                GetProjectList.GetMyProjectResponseInfo.toDto(projectRepository.findAllProgressingProjects(memberId)),
                GetProjectList.GetCompanyProjectResponseInfo.toDto(projectRepository.findAllCompanyProgressingProjects(companyId))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public GetProjectDetail.Response getProjectDetail(Long projectId) {
        return new GetProjectDetail.Response(
                projectRepository.getProjectDetail(projectId),
                projectStepRepository.findStepProcessRate(projectId),
                checkRequestRepository.findAllApplicationLists(projectId)
        );
    }
}
