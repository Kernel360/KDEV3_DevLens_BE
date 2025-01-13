package com.seveneleven.project.service;

import com.seveneleven.project.dto.*;
import jakarta.servlet.http.HttpServletRequest;

public interface ProjectChecklistService {
    GetStepChecklist.Response getStepChecklist(Long stepId);
    PostProjectChecklist.Response postProjectChecklist(PostProjectChecklist.Request postProjectChecklist);
    PostProjectChecklistApplication.Response postProjectChecklistApplication(
            PostProjectChecklistApplication.Request requestDto,
            HttpServletRequest request
    );
    PutProjectChecklist.Response putProjectChecklist(PutProjectChecklist.Request putProjectChecklist);
    DeleteProjectChecklist.Response deleteProjectChecklist(Long checklistId);
    PostProjectChecklistAccept.Response postProjectAccept(Long applicationId, Long memberId, HttpServletRequest request);
}
