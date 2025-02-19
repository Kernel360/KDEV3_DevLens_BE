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
    public List<ProjectTag> store(Project project, List<String> tagNameList, List<ProjectTag> projectTags) {
        if(tagNameList == null || tagNameList.isEmpty()) {
            return projectTags;
        }

        // TODO - 리스트 확인 후 추가 및 삭제 처리
        projectTagRepository.deleteAll(projectTags);

        List<ProjectTag> ProjectTags = tagNameList
                .stream()
                .map(tagName -> ProjectTag.create(project, tagName))
                .toList();

        projectTagRepository.saveAll(ProjectTags);

        return ProjectTags;
    }
}
