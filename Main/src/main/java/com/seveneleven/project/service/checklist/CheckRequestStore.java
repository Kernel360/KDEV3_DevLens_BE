package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckRequestHistory;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.project.dto.PostProjectChecklistApplication;

public interface CheckRequestStore {

    CheckRequest checkRequestStore(
            Checklist checklist,
            PostProjectChecklistApplication.Request requestDto,
            Member member,
            String ipAddress
    );

    CheckRequestHistory checkRequestHistoryStore(CheckRequest checkRequest);

    CheckRequest acceptCheckRequest(CheckRequest checkRequest);
    CheckRequest rejectCheckRequest(CheckRequest checkRequest);
}
