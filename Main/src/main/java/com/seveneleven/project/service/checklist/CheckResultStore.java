package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.project.dto.PostProjectChecklistAccept;
import com.seveneleven.project.dto.PostProjectChecklistReject;

public interface CheckResultStore {

    PostProjectChecklistAccept.Response postApplicationAccept(
            CheckRequest requestDto,
            Member member,
            String processorIp
    );

    PostProjectChecklistReject.Response postApplicationReject(
            CheckRequest checkRequest,
            Member member,
            String rejectionReason,
            String processorIp
    );
}
