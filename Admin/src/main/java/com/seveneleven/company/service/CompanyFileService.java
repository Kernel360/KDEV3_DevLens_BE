package com.seveneleven.company.service;

import com.seveneleven.util.file.dto.FileMetadataResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyFileService {
    @Transactional
    void uploadLogoImage(MultipartFile file, Long companyId, Long uploaderId);

    @Transactional(readOnly = true)
    FileMetadataResponse getLogoImage(Long companyId);

    @Transactional
    void deleteLogoImage(Long companyId, Long deleterId);
}
