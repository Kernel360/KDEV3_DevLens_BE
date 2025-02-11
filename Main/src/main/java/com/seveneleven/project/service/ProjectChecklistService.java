package com.seveneleven.project.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckResult;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.*;
import com.seveneleven.util.file.dto.FileMetadataDto;
import com.seveneleven.util.file.dto.LinkResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectChecklistService {
    GetApplication.Response getApplicationDetail(Long applicationId);

    List<FileMetadataDto> getApplicationFiles(Long applicationId);

    List<LinkResponse> getApplicationLinks(Long applicationId);

    PostProjectChecklist.Response postProjectChecklist(ProjectStep projectStep,
                                                       PostProjectChecklist.Request postProjectChecklist);
    Checklist getChecklist(Long checklistId);

    PostProjectChecklistApplication.Response postProjectChecklistApplication(
            Checklist checklist,
            Member member,
            PostProjectChecklistApplication.Request requestDto,
            String ip
    );

    void postProjectChecklistApplicationFiles(
            Long checklistId,
            Long applicationId,
            Long uploaderId,
            List<MultipartFile> files);

    PutProjectChecklist.Response putProjectChecklist(Checklist checklist,
                                                     PutProjectChecklist.Request putProjectChecklist);

    PostProjectChecklistAccept.Response postProjectAccept(Long applicationId, Long memberId, HttpServletRequest request);
    PostProjectChecklistReject.Response postProjectReject(
            CheckRequest checkRequest,
            PostProjectChecklistReject.Request requestDto,
            Member member,
            String ip
    );

    CheckRequest getCheckRequest(Long checklistId);
    CheckResult getCheckResult(Long checklistId);

    void postCheckRejectFiles(
            Long applicationId,
            Long uploaderId,
            List<MultipartFile>files
    );

    List<FileMetadataDto> getChecklistRejectFiles(Long applicationId);

    List<LinkResponse> getChecklistRejectLinks(Long applicationId);

    DeleteProjectChecklist.Response deleteProjectChecklist(Checklist checklist);

}
