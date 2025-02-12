package com.seveneleven.board.service;

import com.seveneleven.util.file.dto.FileMetadataResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostFileService {
    @Transactional
    void uploadPostFiles(List<MultipartFile> files, Long postId, Long uploaderId);

    @Transactional(readOnly = true)
    List<FileMetadataResponse> getPostFiles(Long postId);

    @Transactional
    void deletePostFile(Long postId, Long fileId, Long deleterId);

    @Transactional
    void deleteAllPostFiles(Long postId, Long deleterId);
}
