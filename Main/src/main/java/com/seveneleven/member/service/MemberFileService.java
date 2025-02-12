package com.seveneleven.member.service;

import com.seveneleven.util.file.dto.FileMetadataResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface MemberFileService {

    void uploadProfileImage(MultipartFile file, Long memberId, Long uploaderId);

    FileMetadataResponse getProfileImage(Long memberId);

    void deleteProfileImage(Long memberId, Long deleterId);
}
