package com.seveneleven.project.service.dashboard;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.repository.CheckRequestRepository;
import com.seveneleven.project.repository.ProjectRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.seveneleven.project.dto.GetProjectList.*;

@Component
@RequiredArgsConstructor
public class ProjectReaderImpl implements ProjectReader {
    private final ProjectRepository projectRepository;
    private final ProjectStepRepository projectStepRepository;
    private final CheckRequestRepository checkRequestRepository;

    @Override
    @Transactional(readOnly = true)
    public Project read(Long projectId) {
        return projectRepository.findByIdAndProjectStatusCodeNot(projectId, ProjectStatusCode.DELETED)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Response getProjectList(Long memberId, Long companyId) {
        List<GetMyProjectResponseInfo> myProject = getMyProjects(memberId);
        List<GetCompanyProjectResponseInfo> companyProject = getCompanyProjects(companyId);

        return Response.toDto(myProject, companyProject);
    }

    @Override
    @Transactional(readOnly = true)
    public GetProjectDetail.Response getProjectDetail(Long projectId) {
        return GetProjectDetail.Response.toDto(
                projectRepository.getProjectDetail(projectId).orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND)),
                projectStepRepository.findStepProcessRate(projectId),
                checkRequestRepository.findAllApplicationLists(projectId)
        );
    }

    private List<GetCompanyProjectResponseInfo> getCompanyProjects(Long companyId) {
        return GetCompanyProjectResponseInfo
                .toDto(projectRepository.findAllCompanyProgressingProjects(companyId));
    }

    private List<GetMyProjectResponseInfo> getMyProjects(Long memberId) {
        return GetMyProjectResponseInfo
                .toDto(projectRepository.findAllProgressingProjects(memberId));
    }
}
