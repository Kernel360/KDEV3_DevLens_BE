package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminProjectReader {
    Project getProject(Long id);
    List<Project> getProcessingProjects();
    Page<Project> getProjectList(Pageable pageable);
    boolean checkProjectExists(String name);
}
