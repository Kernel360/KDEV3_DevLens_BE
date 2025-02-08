package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.file.Link;
import com.seveneleven.entity.member.Member;
import org.springframework.transaction.annotation.Transactional;

public interface CheckRequestLinkHistoryStore {
    @Transactional
    void saveRequestLinkUploadHistory(Link uploadedLinkEntity, Member registrant);

    @Transactional
    void saveRejectionLinkUploadHistory(Link uploadedLinkEntity, Member registrant);
}
