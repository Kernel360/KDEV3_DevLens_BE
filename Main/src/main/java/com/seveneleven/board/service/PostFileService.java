package com.seveneleven.board.service;

import com.seveneleven.util.file.dto.FileMetadataResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostFileService {

    List<FileMetadataResponse> uploadPostFiles(List<MultipartFile> files, Long postId, Long uploaderId);

    List<FileMetadataResponse> getPostFiles(Long postId);

    void deletePostFile(Long postId, Long fileId, Long deleterId);

    void deleteAllPostFiles(Long postId, Long deleterId);
}
