package com.seveneleven.project.service.step;

import com.seveneleven.entity.project.Project;
import com.seveneleven.project.dto.DeleteProjectStep;
import com.seveneleven.project.dto.PostProjectStep;
import com.seveneleven.project.dto.PutProjectStep;

public interface StepStore {

    PostProjectStep.Response store(
            PostProjectStep.Request requestDto,
            Project project
    );

    PutProjectStep.Response edit(
            PutProjectStep.Request requestDto,
            Project project
    );

    DeleteProjectStep.Response delete(
            DeleteProjectStep.Request requestDto,
            Project project
    );

}
