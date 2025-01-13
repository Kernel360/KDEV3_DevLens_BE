package com.seveneleven.project.service.step;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.DeleteProjectStep;
import com.seveneleven.project.dto.PostProjectStep;
import com.seveneleven.project.dto.PutProjectStep;
import com.seveneleven.project.repository.ChecklistRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StepStoreImpl implements StepStore {

    private final ProjectStepRepository projectStepRepository;
    private final ChecklistRepository checklistRepository;

    @Override
    public PostProjectStep.Response store(
            PostProjectStep.Request requestDto,
            Project project
    ) {
        ProjectStep projectStep = requestDto.toEntity(project);
        projectStepRepository.save(projectStep);

        for(PostProjectStep.PostChecklist checklist : requestDto.getChecklists()) {
            checklistRepository.save(checklist.toEntity(projectStep));
        }

        return PostProjectStep.Response.toDto(projectStep, requestDto.getChecklists());
    }

    @Override
    public PutProjectStep.Response edit(
            PutProjectStep.Request requestDto,
            Project project
    ) {
        return null;
    }

    @Override
    public DeleteProjectStep.Response delete(
            DeleteProjectStep.Request requestDto,
            Project project
    ) {
        return null;
    }
}
