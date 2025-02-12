package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProjectTagServiceImpl implements AdminProjectTagService {

    private final AdminProjectTagStore adminProjectTagStore;

    @Override
    @Transactional
    public List<ProjectTag> storeProjectTags(Project project, List<String> tags) {
        return adminProjectTagStore.store(project, tags);
    }
}
