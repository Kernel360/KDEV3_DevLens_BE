package com.seveneleven.company.service;

import com.seveneleven.entity.file.FileMetadata;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyFileHistoryService {
    @Transactional
    void registerLogoImageHistory(FileMetadata uploadedFileEntity, Long registrantId);

    @Transactional
    void deleteLogoImageHistory(FileMetadata deletedFileEntity, Long registrantId);
}
