package com.seveneleven.util.file;

import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.entity.file.constant.FileCategory;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB 제한

    /**
     * 파일 전체 검증 메서드
     *
     * @param file        업로드된 파일
     * @param fileCategory 파일 카테고리
     */
    public static void validateFile(MultipartFile file, FileCategory fileCategory) {
        //파일이 비어있거나 크기가 0이면 예외 발생
        if (ObjectUtils.isEmpty(file)) {
            throw new BusinessException(ErrorCode.FILE_NOT_EXIST_ERROR);
        }

        String fileName = file.getOriginalFilename();
        //파일의 이름이 비어있거나 길이가 0이면 예외 발생
        if (StringUtils.isBlank(fileName)) {
            throw new BusinessException(ErrorCode.INVALID_FILE_NAME_ERROR);
        }

        validateFileExtension(fileName, fileCategory); // 파일 확장자 검증
        validateMimeType(file.getContentType(), fileCategory); // MIME 타입 검증
        validateFileSize(file.getSize()); // 파일 크기 검증
    }

    /**
     * 파일 확장자 검증
     */
    private static void validateFileExtension(String fileName, FileCategory fileCategory) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        if (!fileCategory.getAllowedExtensions().contains(fileExtension)) {
            throw new BusinessException(ErrorCode.FORMAT_NOT_PERMITTED_ERROR);
        }
    }

    /**
     * MIME 타입 검증
     */
    private static void validateMimeType(String mimeType, FileCategory fileCategory) {
        if (!fileCategory.getAllowedMimeTypes().contains(mimeType)) {
            throw new BusinessException(ErrorCode.MIME_NOT_PERMITTED_ERROR);
        }
    }

    /**
     * 파일 크기 검증
     */
    private static void validateFileSize(long fileSize) {
        if (fileSize > MAX_FILE_SIZE) {
            throw new BusinessException(ErrorCode.FILE_SIZE_EXCEED_ERROR);
        }
    }
}