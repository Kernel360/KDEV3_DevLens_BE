package com.seveneleven.board.service;

import com.seveneleven.util.file.dto.FileMetadataResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostFileService {

    void uploadPostFiles(List<MultipartFile> files, Long postId, Long uploaderId, String uploaderRole);

    List<FileMetadataResponse> getPostFiles(Long postId);

    void deletePostFile(Long postId, Long fileId, Long deleterId, String deleterRole);

    void deleteAllPostFiles(Long postId, Long deleterId, String deleterRole);
}
