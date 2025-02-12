package com.seveneleven.board.service;

import com.seveneleven.util.file.dto.LinkInput;
import com.seveneleven.util.file.dto.LinkResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostLinkService {

    void uploadPostLinks(List<LinkInput> linkInputs, Long postId, Long uploaderId);

    List<LinkResponse> getPostLinks(Long postId);

    void deletePostLink(Long postId, Long linkId, Long deleterId);

    void deleteAllPostLinks(Long postId);
}
