package com.seveneleven.project.service;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.checklist.ChecklistReader;
import com.seveneleven.project.service.checklist.ChecklistStore;
import com.seveneleven.project.service.step.StepReader;
import com.seveneleven.project.service.step.StepStore;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectStepServiceImpl implements ProjectStepService {

    private final StepReader stepReader;
    private final StepStore stepStore;
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
        List<ProjectStep> projectSteps = stepReader.getProjectStep(projectId);

        GetProjectStep.Response response = GetProjectStep.Response.create(projectId);

        projectSteps.forEach(stepInfo -> {
            response.add(stepInfo, checklistReader.read(stepInfo.getId()));
        });

        return response;
    }

    @Override
    @Transactional
    public PostProjectStep.Response postProjectStep(Project project, PostProjectStep.Request requestDto) {
        List<Integer> orderList = stepReader.getStepOrderList(project.getId());

        if(orderList.size() >= 10) {
            throw new BusinessException(ErrorCode.PROJECT_STEP_MAX_SIZE);
        }

        Integer order = getStepOrder(orderList, requestDto.getStepOrderNumber() - 1);

        return stepStore.store(requestDto, project, order);
    }

    @Override
    @Transactional
    public PutProjectStep.Response putProjectStep(ProjectStep projectStep, PutProjectStep.Request requestDto) {
        log.info("request.order : {}", requestDto.getStepOrder());
        List<Integer> orderList = stepReader.getStepOrderList(projectStep.getProject().getId());
        orderList.removeIf(order -> order.equals(projectStep.getStepOrder()));

        Integer order = getStepOrder(orderList, requestDto.getStepOrder() - 1);
        log.info("order : {}", order);
        return stepStore.edit(requestDto, projectStep, order);
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

    private Integer getStepOrder(List<Integer> orderList, Integer target) {
        if(target == null || target < 0) {
            throw new BusinessException(ErrorCode.PROJECT_STEP_ORDER_MORE_THAN_ZERO);
        }
        if(orderList == null || orderList.isEmpty()) {
            return 100;
        }
        if(target == 0) {
            Integer find = orderList.get(0);
            return find / 2;
        }
        if(target >= orderList.size()) {
            return orderList.get(orderList.size() - 1) + 100;
        }
        return (orderList.get(target) + orderList.get(target - 1)) / 2;
    }
}
