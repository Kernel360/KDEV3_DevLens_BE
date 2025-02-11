package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.CheckRequest;

import java.util.List;

public interface CheckRequestReader {
    CheckRequest read(Long applicationId);
    List<CheckRequest> readByChecklistId(Long checklistId);
}
