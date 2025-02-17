package com.seveneleven.project.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckResult;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.checklist.*;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.GetIpUtil;
import com.seveneleven.util.file.dto.FileMetadataResponse;
import com.seveneleven.util.file.dto.LinkResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectChecklistServiceImpl implements ProjectChecklistService {
    private final ChecklistReader checklistReader;
    private final ChecklistStore checklistStore;
    private final CheckRequestReader checkRequestReader;
    private final CheckResultReader checkResultReader;
    private final CheckRequestFileReader checkRequestFileReader;
    private final CheckRequestLinkReader checkRequestLinkReader;
    private final CheckRequestStore checkRequestStore;
    private final CheckResultStore checkResultStore;
    private final CheckRequestFileStore checkRequestFileStore;
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
    public GetApplication.Response getApplicationDetail(Long applicationId) {
        CheckRequest checkRequest = checkRequestReader.read(applicationId);
        return GetApplication.Response.toDto(checkRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileMetadataResponse> getApplicationFiles(Long applicationId){
        CheckRequest checkRequest = checkRequestReader.read(applicationId);

        List<FileMetadataResponse> fileDtoList = checkRequestFileReader.readCheckRequestFiles(checkRequest);

        return fileDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkResponse> getApplicationLinks(Long applicationId) {
        CheckRequest checkRequest = checkRequestReader.read(applicationId);

        List<LinkResponse> linkResponses =  checkRequestLinkReader.readCheckRequestLinks(checkRequest);

        return linkResponses;
    }

    @Override
    @Transactional
    public PostProjectChecklistApplication.Response postProjectChecklistApplication(
            Checklist checklist,
            Member member,
            PostProjectChecklistApplication.Request requestDto,
            String ip
    ) {
        CheckRequest checkRequest = checkRequestStore.checkRequestStore(checklist, requestDto, member, ip);
        checklist.setChecklistApplication();
        checkRequestStore.checkRequestHistoryStore(checkRequest);

        return PostProjectChecklistApplication.Response.toDto(checkRequest);
    }

    @Override
    @Transactional
    public void postProjectChecklistApplicationFiles(
            Long checklistId,
            Long applicationId,
            Long uploaderId,
            List<MultipartFile>files){

        //1. 체크리스트 아이디 판별
        Checklist checklist = getChecklist(checklistId);

        //2. 요청 아이디 판별
        CheckRequest checkRequest = checkRequestReader.read(applicationId);

        //3. 업로더 판별
        Member member = getMember(uploaderId);

        //4. 파일 업로드
        checkRequestFileStore.checkRequestFileStore(files, checkRequest, member);
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
        checklistStore.accept(checkRequest.getChecklist(), memberId);

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
                checkRequest, member, ip, requestDto
        );
    }

    @Override
    public CheckRequest getCheckRequest(Long checklistId) {
        return checkRequestReader.read(checklistId);
    }

    @Override
    public CheckResult getCheckResult(Long checklistId) {
        return checkResultReader.read(checklistId);
    }

    @Override
    @Transactional
    public void postCheckRejectFiles(
            Long applicationId,
            Long uploaderId,
            List<MultipartFile>files
    ) {
        //1. 요청 아이디 판별
        CheckRequest checkRequest = checkRequestReader.read(applicationId);

        //2. 업로더 판별
        Member member = getMember(uploaderId);

        //3. 파일 업로드
        checkRequestFileStore.checkRequestRejectFileStore(files, checkRequest, member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileMetadataResponse> getChecklistRejectFiles(Long applicationId) {
        CheckRequest checkRequest = checkRequestReader.read(applicationId);

        List<FileMetadataResponse> fileDtoList = checkRequestFileReader.readCheckRequestRejectFiles(checkRequest);

        return fileDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkResponse> getChecklistRejectLinks(Long applicationId) {
        CheckRequest checkRequest = checkRequestReader.read(applicationId);

        List<LinkResponse> linkResponses =  checkRequestLinkReader.readCheckRequestRejectLinks(checkRequest);

        return linkResponses;
    }

    // TODO - 회원 쪽 서비스 코드 교체 필요
    private Member getMember(Long id) {
        return memberRepository.findByIdAndStatus(
                id,
                MemberStatus.ACTIVE
        ).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
