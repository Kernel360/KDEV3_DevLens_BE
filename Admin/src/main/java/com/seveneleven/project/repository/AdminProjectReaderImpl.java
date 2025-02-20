package com.seveneleven.project.repository;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import com.seveneleven.project.exception.ProjectNotFoundException;
import com.seveneleven.project.service.AdminProjectReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminProjectReaderImpl implements AdminProjectReader {
    private final AdminProjectRepository adminProjectRepository;
    @Override
    public Project getProject(Long id) {
        return adminProjectRepository.findByIdAndProjectStatusCodeNot(id, ProjectStatusCode.DELETED).orElseThrow(ProjectNotFoundException::new);
    }

    @Override
    public List<Project> getProcessingProjects() {
        return adminProjectRepository.findAllByProjectStatusCode(ProjectStatusCode.IN_PROGRESS);
    }

    @Override
    public Page<Project> getProjectList(Pageable pageable) {
        return adminProjectRepository.findAllByProjectStatusCodeNot(pageable, ProjectStatusCode.DELETED);
    }

    @Override
    public boolean checkProjectExists(String name) {
        return adminProjectRepository.findByProjectNameAndProjectStatusCodeNot(name, ProjectStatusCode.DELETED)
                .isPresent();
    }
}