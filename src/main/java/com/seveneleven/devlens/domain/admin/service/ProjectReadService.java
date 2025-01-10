package com.seveneleven.devlens.domain.admin.service;

import com.seveneleven.devlens.domain.admin.dto.GetProject;
import com.seveneleven.devlens.domain.admin.dto.PaginatedResponse;
import com.seveneleven.devlens.domain.admin.exception.ProjectNotFoundException;
import com.seveneleven.devlens.domain.admin.repository.AdminProjectRepository;
import com.seveneleven.devlens.domain.admin.repository.ProjectResponseConverter;
import com.seveneleven.devlens.domain.project.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectReadService {
    private final AdminProjectRepository projectRepository;
    private final ProjectResponseConverter projectResponseConverter;
    private final int PAGE_SIZE = 10;

    @Transactional(readOnly = true)
    public GetProject.Response getProject(
            Long id
    ) {
        return projectRepository.findById(id)
                .map(projectResponseConverter::toDTO)
                .orElseThrow(ProjectNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<GetProject.Response> getListOfProject(
            Integer page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id").descending());
        Page<Project> projects = projectRepository.findAll(pageable);
        if (projects.getContent().isEmpty()) {
            throw new ProjectNotFoundException();
        }
        return PaginatedResponse.createPaginatedResponse(projects.map(projectResponseConverter::toDTO));
    }
}