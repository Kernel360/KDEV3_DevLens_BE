package com.seveneleven.member.service;

import com.seveneleven.entity.file.FileMetadata;
import org.springframework.transaction.annotation.Transactional;

public interface MemberFileHistoryService {
    @Transactional
    void registerProfileImageHistory(FileMetadata uploadedFileEntity, Long registrantId);

    @Transactional
    void deleteLogoImageHistory(FileMetadata deletedFileEntity, Long registrantId);
}
