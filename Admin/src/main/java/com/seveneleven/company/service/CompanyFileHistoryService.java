package com.seveneleven.company.service;

import com.seveneleven.entity.file.FileMetadata;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyFileHistoryService {

    void registerLogoImageHistory(FileMetadata uploadedFileEntity, Long registrantId);

    void deleteLogoImageHistory(FileMetadata deletedFileEntity, Long registrantId);
}
