package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectTag;
import com.seveneleven.project.repository.AdminProjectTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminProjectTagStoreImpl implements AdminProjectTagStore {

    private final AdminProjectTagRepository projectTagRepository;

    @Override
    public List<ProjectTag> store(Project project, List<String> tagNameList) {
        if(tagNameList == null || tagNameList.isEmpty()) {
            return List.of();
        }

        List<ProjectTag> ProjectTags = tagNameList
                .stream()
                .map(tagName -> ProjectTag.create(project, tagName))
                .toList();

        projectTagRepository.saveAll(ProjectTags);

        return ProjectTags;
    }
}
