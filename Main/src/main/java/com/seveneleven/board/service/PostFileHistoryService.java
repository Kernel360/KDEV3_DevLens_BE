package com.seveneleven.board.service;

import com.seveneleven.entity.file.FileMetadata;
import org.springframework.transaction.annotation.Transactional;

public interface PostFileHistoryService {
    @Transactional
    void registerPostFileHistory(FileMetadata uploadedFileEntity, Long registrantId);

    @Transactional
    void deletePostFileHistory(FileMetadata deletedFileEntity, Long registrantId);
}
