package com.seveneleven.project.controller;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckResult;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.member.service.MemberService;
import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.ProjectCheckRequestService;
import com.seveneleven.project.service.ProjectChecklistService;
import com.seveneleven.project.service.ProjectStepService;
import com.seveneleven.project.service.checklist.CheckResultReader;
import com.seveneleven.util.GetIpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectChecklistFacade {

    private final ProjectChecklistService projectChecklistService;
    private final ProjectStepService projectStepService;
    private final ProjectCheckRequestService projectCheckRequestService;
    private final MemberService memberService;
    private final GetIpUtil getIpUtil;
    private final CheckResultReader checkResultReader;

    /**
     * 함수명 : getProjectChecklistApplication
     * 승인 요청 상세를 반환하는 함수
     */
    public GetApplication.Response getProjectChecklistApplication(Long applicationId) {
        return projectChecklistService.getApplicationDetail(applicationId);
    }

    /**
     * 함수명 : getStepChecklist
     * 해당 단계의 체크리스트 목록을 반환하는 함수
     */
    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return projectStepService.getStepChecklist(stepId);
    }

    /**
     * 함수명 : postProjectChecklist
     * 체크리스트를 추가하는 함수
     */
    @Transactional
    public PostProjectChecklist.Response postProjectChecklist(
            Long stepId,
            PostProjectChecklist.Request postProjectChecklist
    ) {
        ProjectStep projectStep = projectStepService.getProjectStepById(stepId);
        return projectChecklistService.postProjectChecklist(projectStep, postProjectChecklist);
    }

    /**
     * 함수명 : putProjectChecklist
     * 체크리스트를 수정하는 함수
     */@Transactional
    public PutProjectChecklist.Response putProjectChecklist(
            Long checklistId,
            PutProjectChecklist.Request putProjectChecklist
    ) {
        Checklist checklist = projectChecklistService.getChecklist(checklistId);
        return projectChecklistService.putProjectChecklist(checklist, putProjectChecklist);
    }

    /**
     * 함수명 : deleteProjectChecklist
     * 체크리스트를 삭제하는 함수
     */
    public DeleteProjectChecklist.Response deleteProjectChecklist(Long checklistId) {
        Checklist checklist = projectChecklistService.getChecklist(checklistId);
        return projectChecklistService.deleteProjectChecklist(checklist);
    }

    /**
     * 함수명 : postProjectChecklistApplication
     * 체크리스트 승인 요청을 등록하는 함수
     */
    public PostProjectChecklistApplication.Response postProjectChecklistApplication(
            Long checklistId,
            Long userId,
            PostProjectChecklistApplication.Request requestDto,
            HttpServletRequest request
    ) {
        Checklist checklist = projectChecklistService.getChecklist(checklistId);
        Member member = memberService.getMember(userId);
        String ip = getIpUtil.getIpAddress(request);
        return projectChecklistService.postProjectChecklistApplication(checklist, member, requestDto, ip);
    }

    /**
     * 함수명 : postProjectChecklistAccept
     * 체크리스트 승인 요청을 승인하는 함수
     */
    public PostProjectChecklistAccept.Response postProjectChecklistAccept(
            Long applicationId,
            Long memberId,
            HttpServletRequest request
    ) {
        return projectChecklistService.postProjectAccept(applicationId, memberId, request);
    }

    /**
     * 함수명 : getApplicationResult
     * 체크리스트 승인 요청 결과를 조회하는 함수
     */
    @Transactional(readOnly = true)
    public GetApplicationResult.Response getApplicationResult(Long applicationId) {
        CheckRequest checkRequest = projectChecklistService.getCheckRequest(applicationId);
        CheckResult checkResult = projectChecklistService.getCheckResult(applicationId);
        return GetApplicationResult.Response.toDto(checkRequest, checkResult);
    }

    /**
     * 함수명 : postProjectChecklistReject
     * 체크리스트 승인 요청을 반려하는 함수
     */
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

    @Transactional(readOnly = true)
    public GetChecklistApplication.Response getChecklistAllApplications(Long checklistId) {
        Checklist checklist = projectChecklistService.getChecklist(checklistId);
        List<CheckRequest> applications =
                projectCheckRequestService.getAllCheckRequestByChecklistId(checklist);

        List<GetChecklistApplication.ChecklistApplication> result =
                projectCheckRequestService.getChecklistApplications(applications);

        return GetChecklistApplication.Response.toDto(result);
    }
}
