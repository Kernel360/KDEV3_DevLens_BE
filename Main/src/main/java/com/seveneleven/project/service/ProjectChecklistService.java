package com.seveneleven.project.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.*;
import jakarta.servlet.http.HttpServletRequest;

public interface ProjectChecklistService {
    GetProjectChecklistApplication.Response getApplicationDetail(Long applicationId);
    PostProjectChecklist.Response postProjectChecklist(ProjectStep projectStep,
                                                       PostProjectChecklist.Request postProjectChecklist);
    Checklist getChecklist(Long checklistId);

    PostProjectChecklistApplication.Response postProjectChecklistApplication(
            Checklist checklist,
            PostProjectChecklistApplication.Request requestDto,
            String ip
    );

    PutProjectChecklist.Response putProjectChecklist(Checklist checklist,
                                                     PutProjectChecklist.Request putProjectChecklist);

    PostProjectChecklistAccept.Response postProjectAccept(Long applicationId, Long memberId, HttpServletRequest request);
    PostProjectChecklistReject.Response postProjectReject(
            CheckRequest checkRequest,
            PostProjectChecklistReject.Request requestDto,
            Member member,
            String ip
    );

    DeleteProjectChecklist.Response deleteProjectChecklist(Checklist checklist);
}
