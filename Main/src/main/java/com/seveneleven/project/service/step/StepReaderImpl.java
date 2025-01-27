package com.seveneleven.project.service.step;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.dto.GetProjectStep;
import com.seveneleven.project.repository.ProjectStepRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StepReaderImpl implements StepReader {

    private final ProjectStepRepository projectStepRepository;

    @Override
    public ProjectStep read(Long stepId) {
        return projectStepRepository.findByIdAndIsActive(stepId, YesNo.YES)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PROJECT_STEP));
    }

    @Override
    public GetProjectStep.Response getProjectStep(Long projectId) {
        List<ProjectStep> stepInfos = projectStepRepository.findByProjectIdAndIsActive(projectId, YesNo.YES);

        List<GetProjectStep.ProjectStepInfo> projectStepInfos = new ArrayList<>();

        for(ProjectStep stepInfo : stepInfos) {
            projectStepInfos.add(GetProjectStep.ProjectStepInfo.toDto(
                    stepInfo,
                    projectStepRepository.findProjectStepChecklist(stepInfo.getId())
            ));
        }

        return new GetProjectStep.Response(projectId, projectStepInfos);
    }
}
