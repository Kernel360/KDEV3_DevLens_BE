package com.seveneleven.project.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.checklist.*;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.GetIpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectChecklistServiceImpl implements ProjectChecklistService {
    private final ChecklistReader checklistReader;
    private final ChecklistStore checklistStore;
    private final CheckRequestReader checkRequestReader;
    private final CheckRequestStore checkRequestStore;
    private final CheckResultStore checkResultStore;

    private final MemberRepository memberRepository;

    private final GetIpUtil getIpUtil;

    @Override
    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return checklistReader.getStepChecklist(stepId);
    }

    @Override
    public PostProjectChecklist.Response postProjectChecklist(PostProjectChecklist.Request postProjectChecklist) {
        return PostProjectChecklist.Response.toDto(checklistStore.storeChecklist(postProjectChecklist));
    }

    @Override
    public GetProjectChecklistApplication.Response getApplicationDetail(Long applicationId) {
        CheckRequest checkRequest = checkRequestReader.read(applicationId);
        return GetProjectChecklistApplication.Response.toDto(checkRequest);
    }

    @Override
    @Transactional
    public PostProjectChecklistApplication.Response postProjectChecklistApplication(
            PostProjectChecklistApplication.Request requestDto,
            HttpServletRequest request
    ) {
        Member member = getMember(requestDto.getRequesterId());
        CheckRequest checkRequest = checkRequestStore.checkRequestStore(requestDto, member, request);
        checkRequestStore.checkRequestHistoryStore(checkRequest);
        return PostProjectChecklistApplication.Response.toDto(checkRequest);
    }

    @Override
    @Transactional
    public PutProjectChecklist.Response putProjectChecklist(PutProjectChecklist.Request putProjectChecklist) {
        return PutProjectChecklist.Response.toDto(checklistStore.updateChecklist(putProjectChecklist));
    }

    @Override
    @Transactional
    public DeleteProjectChecklist.Response deleteProjectChecklist(Long checklistId) {
        Checklist checklist = checklistReader.getChecklist(checklistId).deleteChecklist();
        return new DeleteProjectChecklist.Response(checklistId, checklist.getIsActive());
    }

    @Override
    @Transactional
    public PostProjectChecklistAccept.Response postProjectAccept(Long applicationId, Long memberId, HttpServletRequest request) {
        CheckRequest checkRequest = checkRequestReader.read(applicationId);
        String processorIp = getIpUtil.getIpAddress(request);
        Member member = getMember(memberId);
        PostProjectChecklistAccept.Response response = checkResultStore.postApplicationAccept(checkRequest, member, processorIp);
        checkRequestStore.acceptCheckRequest(checkRequest);
        checklistStore.accept(checkRequest.getChecklist());
        return response;
    }

    // TODO - 회원 쪽 서비스 코드 교체 필요
    private Member getMember(Long id) {
        return memberRepository.findByIdAndStatus(
                id,
                MemberStatus.ACTIVE
        ).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
