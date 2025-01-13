package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckRequestHistory;
import com.seveneleven.project.dto.PostProjectChecklistApplication;
import jakarta.servlet.http.HttpServletRequest;

public interface CheckRequestStore {

    CheckRequest checkRequestStore(
            PostProjectChecklistApplication.Request requestDto,
            Member member,
            HttpServletRequest request
    );

    CheckRequestHistory checkRequestHistoryStore(CheckRequest checkRequest);

    void acceptCheckRequest(CheckRequest checkRequest);
    void rejectCheckRequest(CheckRequest checkRequest);
}
