package com.seveneleven.project.service;

import com.seveneleven.project.dto.*;

public interface ProjectStepService {
    GetProjectStep.Response getProjectStep(Long projectId);
    GetStepChecklist.Response getStepChecklist(Long stepId);
    PostProjectStep.Response postProjectStep(PostProjectStep.Request requestDto);
    PutProjectStep.Response putProjectStep(PutProjectStep.Request requestDto);
    DeleteProjectStep.Response deleteProjectStep(Long projectId, Long stepId);
}
