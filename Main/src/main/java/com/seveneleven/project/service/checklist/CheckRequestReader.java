package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.CheckRequest;

public interface CheckRequestReader {
    CheckRequest read(Long applicationId);
}
