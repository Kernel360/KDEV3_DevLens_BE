package com.seveneleven.board.service;

import com.seveneleven.util.file.dto.LinkInput;
import com.seveneleven.util.file.dto.LinkResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostLinkService {
    @Transactional
    void uploadPostLinks(List<LinkInput> linkInputs, Long postId, Long uploaderId);

    @Transactional(readOnly = true)
    List<LinkResponse> getPostLinks(Long postId);

    @Transactional
    void deletePostLink(Long postId, Long linkId, Long deleterId);

    @Transactional
    void deleteAllPostLinks(Long postId);
}
