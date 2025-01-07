package com.seveneleven.devlens.global.util.file;

import com.seveneleven.devlens.global.exception.BusinessException;
import com.seveneleven.devlens.global.response.ErrorCode;
import com.seveneleven.devlens.global.util.file.constant.FileCategory;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024; // 20MB 제한

    /**
     * 파일 전체 검증 메서드
     *
     * @param file        업로드된 파일
     * @param fileCategoryName 파일 카테고리명
     */
    public static void validateFile(MultipartFile file, String fileCategoryName) {
        if (ObjectUtils.isEmpty(file)) {
            throw new BusinessException(ErrorCode.FILE_NOT_EXIST_ERROR);
        }

        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)) {
            throw new BusinessException(ErrorCode.INVALID_FILE_NAME_ERROR);
        }

        FileCategory fileCategory = validateFileCategory(fileCategoryName); // 파일 카테고리 검증
        validateFileExtension(fileName, fileCategory); // 파일 확장자 검증
        validateMimeType(file.getContentType(), fileCategory); // MIME 타입 검증
        validateFileSize(file.getSize()); // 파일 크기 검증
    }

    /**
     * 파일 카테고리 검증
     */
    private static FileCategory validateFileCategory(String fileCategoryName) {
        try{
            return FileCategory.valueOf(fileCategoryName);
        } catch (IllegalArgumentException e){
            throw new BusinessException(ErrorCode.INVALID_FILE_CATEGORY_ERROR);
        }
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