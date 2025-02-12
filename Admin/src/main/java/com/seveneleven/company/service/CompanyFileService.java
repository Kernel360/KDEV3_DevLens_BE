package com.seveneleven.company.service;

import com.seveneleven.util.file.dto.FileMetadataResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyFileService {

    void uploadLogoImage(MultipartFile file, Long companyId, Long uploaderId);

    FileMetadataResponse getLogoImage(Long companyId);

    void deleteLogoImage(Long companyId, Long deleterId);
}
