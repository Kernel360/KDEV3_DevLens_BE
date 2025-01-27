package com.seveneleven.project.controller;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.member.service.MemberService;
import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.ProjectCheckRequestService;
import com.seveneleven.project.service.ProjectChecklistService;
import com.seveneleven.project.service.ProjectStepService;
import com.seveneleven.util.GetIpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectChecklistFacade {

    private final ProjectChecklistService projectChecklistService;
    private final ProjectStepService projectStepService;
    private final ProjectCheckRequestService projectCheckRequestService;
    private final MemberService memberService;
    private final GetIpUtil getIpUtil;

    public GetProjectChecklistApplication.Response getProjectChecklistApplication(Long applicationId) {
        return projectChecklistService.getApplicationDetail(applicationId);
    }

    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return projectStepService.getStepChecklist(stepId);
    }

    public PostProjectChecklist.Response postProjectChecklist(Long stepId, PostProjectChecklist.Request postProjectChecklist) {
        ProjectStep projectStep = projectStepService.getProjectStepById(stepId);
        return projectChecklistService.postProjectChecklist(projectStep, postProjectChecklist);
    }

    public PutProjectChecklist.Response putProjectChecklist(Long checklistId,
                                                            PutProjectChecklist.Request putProjectChecklist) {
        Checklist checklist = projectChecklistService.getChecklist(checklistId);
        return projectChecklistService.putProjectChecklist(checklist, putProjectChecklist);
    }

    public DeleteProjectChecklist.Response deleteProjectChecklist(Long checklistId) {
        Checklist checklist = projectChecklistService.getChecklist(checklistId);
        return projectChecklistService.deleteProjectChecklist(checklist);
    }

    public PostProjectChecklistApplication.Response postProjectChecklistApplication(
            Long checklistId,
            PostProjectChecklistApplication.Request requestDto,
            HttpServletRequest request
    ) {
        Checklist checklist = projectChecklistService.getChecklist(checklistId);
        String ip = getIpUtil.getIpAddress(request);
        return projectChecklistService.postProjectChecklistApplication(checklist, requestDto, ip);
    }

    public PostProjectChecklistAccept.Response postProjectChecklistAccept(
            Long applicationId,
            Long memberId,
            HttpServletRequest request
    ) {
        return projectChecklistService.postProjectAccept(applicationId, memberId, request);
    }

    public PostProjectChecklistReject.Response postProjectChecklistReject(
            Long applicationId,
            PostProjectChecklistReject.Request requestDto,
            Long memberId,
            HttpServletRequest request
    ) {
        Member member = memberService.getMember(memberId);
        CheckRequest checkRequest = projectCheckRequestService.getCheckRequestById(applicationId);
        String ip = getIpUtil.getIpAddress(request);

        return projectChecklistService.postProjectReject(checkRequest, requestDto, member, ip);
    }
}
