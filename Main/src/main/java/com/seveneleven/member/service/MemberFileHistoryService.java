package com.seveneleven.member.service;

import com.seveneleven.entity.file.FileMetadata;
import org.springframework.transaction.annotation.Transactional;

public interface MemberFileHistoryService {

    void registerProfileImageHistory(FileMetadata uploadedFileEntity, Long registrantId);

    void deleteLogoImageHistory(FileMetadata deletedFileEntity, Long registrantId);
}
