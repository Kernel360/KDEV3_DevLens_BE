package com.seveneleven.board.service;

import com.seveneleven.entity.file.FileMetadata;
import org.springframework.transaction.annotation.Transactional;

public interface PostFileHistoryService {

    void registerPostFileHistory(FileMetadata uploadedFileEntity, Long registrantId);

    void deletePostFileHistory(FileMetadata deletedFileEntity, Long registrantId);
}
