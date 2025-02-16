package com.seveneleven.project.service.step;

import com.seveneleven.entity.project.ProjectStep;

import java.util.List;

public interface StepReader {
    ProjectStep read(Long stepId);
    List<ProjectStep> getProjectStep(Long projectId);
    List<Integer> getStepOrderList(Long projectId);
}
