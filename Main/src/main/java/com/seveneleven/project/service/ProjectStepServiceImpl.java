package com.seveneleven.project.service;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.checklist.ChecklistReader;
import com.seveneleven.project.service.checklist.ChecklistStore;
import com.seveneleven.project.service.dashboard.ProjectReader;
import com.seveneleven.project.service.step.StepReader;
import com.seveneleven.project.service.step.StepStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectStepServiceImpl implements ProjectStepService {

    private final StepReader stepReader;
    private final StepStore stepStore;
    private final ProjectReader projectReader;
    private final ChecklistReader checklistReader;
    private final ChecklistStore checklistStore;

    @Override
    public ProjectStep getProjectStepById(Long stepId) {
        return stepReader.read(stepId);
    }

    @Override
    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return checklistReader.getStepChecklist(stepId);
    }

    @Override
    public GetProjectStep.Response getProjectStep(Long projectId) {
        return stepReader.getProjectStep(projectId);
    }

    @Override
    @Transactional
    public PostProjectStep.Response postProjectStep(Project project, PostProjectStep.Request requestDto) {
        return stepStore.store(requestDto, project);
    }

    @Override
    @Transactional
    public PutProjectStep.Response putProjectStep(ProjectStep projectStep, PutProjectStep.Request requestDto) {
        return stepStore.edit(requestDto, projectStep);
    }

    @Override
    @Transactional
    public DeleteProjectStep.Response deleteProjectStep(Long projectId, Long stepId) {
        List<Checklist> checklists = checklistReader.read(stepId);
        ProjectStep projectStep = stepReader.read(stepId);
        checklistStore.deleteAll(checklists);
        stepStore.delete(projectStep);
        return DeleteProjectStep.Response.toDto(projectId, stepId);
    }
}
