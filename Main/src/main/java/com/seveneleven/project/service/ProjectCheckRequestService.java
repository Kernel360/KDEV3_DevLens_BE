package com.seveneleven.project.service;

import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.project.dto.GetChecklistApplication;

import java.util.List;

public interface ProjectCheckRequestService {
    CheckRequest getCheckRequestById(Long id);
    List<CheckRequest> getAllCheckRequestByChecklistId(Checklist checklist);
    List<GetChecklistApplication.ChecklistApplication> getChecklistApplications(List<CheckRequest> applications);
}
