package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.checklist.ChecklistReader;
import com.seveneleven.project.service.dashboard.ProjectReader;
import com.seveneleven.project.service.step.StepReader;
import com.seveneleven.project.service.step.StepStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectStepServiceImpl implements ProjectStepService {

    private final StepReader stepReader;
    private final StepStore stepStore;
    private final ProjectReader projectReader;
    private final ChecklistReader checklistReader;

    @Override
    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return checklistReader.getStepChecklist(stepId);
    }

    @Override
    public GetProjectStep.Response getProjectStep(Long projectId) {
        return stepReader.getProjectStep(projectId);
    }

    @Override
    public PostProjectStep.Response postProjectStep(PostProjectStep.Request requestDto) {
        Project project = projectReader.read(requestDto.getProjectId());
        return stepStore.store(requestDto, project);
    }

    @Override
    public PutProjectStep.Response putProjectStep(PutProjectStep.Request requestDto) {
        return null;
    }

    @Override
    public DeleteProjectStep.Response deleteProjectStep(Long projectId, Long stepId) {
        return null;
    }
}
