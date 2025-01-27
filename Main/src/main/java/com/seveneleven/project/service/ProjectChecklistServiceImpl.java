package com.seveneleven.project.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
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
    public Checklist getChecklist(Long checklistId) {
        return checklistReader.getChecklist(checklistId);
    }

    @Override
    public PostProjectChecklist.Response postProjectChecklist(
            ProjectStep projectStep,
            PostProjectChecklist.Request postProjectChecklist
    ) {
        return PostProjectChecklist.Response.toDto(checklistStore.storeChecklist(projectStep, postProjectChecklist));
    }

    @Override
    public GetProjectChecklistApplication.Response getApplicationDetail(Long applicationId) {
        CheckRequest checkRequest = checkRequestReader.read(applicationId);
        return GetProjectChecklistApplication.Response.toDto(checkRequest);
    }

    @Override
    @Transactional
    public PostProjectChecklistApplication.Response postProjectChecklistApplication(
            Checklist checklist,
            PostProjectChecklistApplication.Request requestDto,
            String ip) {
        Member member = getMember(requestDto.getRequesterId());

        CheckRequest checkRequest = checkRequestStore.checkRequestStore(checklist, requestDto, member, ip);
        checkRequestStore.checkRequestHistoryStore(checkRequest);

        return PostProjectChecklistApplication.Response.toDto(checkRequest);
    }

    @Override
    @Transactional
    public PutProjectChecklist.Response putProjectChecklist(
            Checklist checklist,
            PutProjectChecklist.Request putProjectChecklist
    ) {
        return PutProjectChecklist.Response.toDto(
                checklistStore.updateChecklist(checklist, putProjectChecklist)
        );
    }

    @Override
    @Transactional
    public DeleteProjectChecklist.Response deleteProjectChecklist(Checklist checklist) {
        checklistStore.delete(checklist);
        return DeleteProjectChecklist.Response.toDto(checklist);
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

    @Override
    @Transactional
    public PostProjectChecklistReject.Response postProjectReject(
            CheckRequest checkRequest,
            PostProjectChecklistReject.Request requestDto,
            Member member,
            String ip
    ) {
        checkRequestStore.rejectCheckRequest(checkRequest);

        return checkResultStore.postApplicationReject(
                checkRequest, member, ip, requestDto.getRejectReason()
        );
    }

    // TODO - 회원 쪽 서비스 코드 교체 필요
    private Member getMember(Long id) {
        return memberRepository.findByIdAndStatus(
                id,
                MemberStatus.ACTIVE
        ).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
