package com.seveneleven.board.service;

import com.seveneleven.entity.file.Link;
import org.springframework.transaction.annotation.Transactional;

public interface PostLinkHistoryServiceImpl {
    @Transactional
    void registerPostLinkHistory(Link uploadedLinkEntity, Long registrantId);

    @Transactional
    void deletePostLinkHistory(Link deletedLinkEntity, Long registrantId);
}
