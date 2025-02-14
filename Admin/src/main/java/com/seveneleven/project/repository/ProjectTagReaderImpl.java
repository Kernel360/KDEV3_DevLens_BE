package com.seveneleven.project.repository;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectTag;
import com.seveneleven.project.service.ProjectTagReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectTagReaderImpl implements ProjectTagReader {
    private final AdminProjectTagRepository projectTagRepository;

    @Override
    public List<ProjectTag> readByProjectId(Long projectId) {
        return projectTagRepository.findByProjectIdAndIsActive(projectId, YesNo.YES);
    }
}
