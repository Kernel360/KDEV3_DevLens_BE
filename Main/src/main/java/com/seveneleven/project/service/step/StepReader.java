package com.seveneleven.project.service.step;

import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.GetProjectStep;

public interface StepReader {
    ProjectStep read(Long stepId);
    GetProjectStep.Response getProjectStep(Long projectStep);
}
