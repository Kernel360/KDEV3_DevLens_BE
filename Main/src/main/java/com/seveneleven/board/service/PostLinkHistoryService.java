package com.seveneleven.board.service;

import com.seveneleven.entity.file.Link;
import org.springframework.transaction.annotation.Transactional;

public interface PostLinkHistoryService {

    void registerPostLinkHistory(Link uploadedLinkEntity, Long registrantId);


    void deletePostLinkHistory(Link deletedLinkEntity, Long registrantId);
}
