package com.seveneleven.util.file.Service;

import com.seveneleven.exception.BusinessException;
import com.seveneleven.util.file.FileValidator;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileMetadataRepository fileMetadataRepository;
    private final S3ClientService s3ClientService;

    private static final double KILOBYTE_CONVERSION_CONSTANT = 1024.0;

    /**
     * 1. 파일 업로드
     * 함수명 : uploadFile
     * @param file 업로드할 파일
     * @param fileCategory 파일 카테고리
     * @param referenceId 파일 참조 ID
     * @return FileMetadataDto 업로드한 파일 메타데이터
     */
    @Transactional
    public FileMetadata uploadFile(MultipartFile file, FileCategory fileCategory, Long referenceId) {
        //1. 파일 검증
        FileValidator.validateFile(file, fileCategory);

        //2. 고유 파일 이름(UUID) 및 S3 키 생성
        //파일명이 없거나 비어있으면 Unknown-File로 설정
        String originalFilename = StringUtils.isEmpty(file.getOriginalFilename()) ? file.getOriginalFilename() : file.getOriginalFilename();

        //UUID 생성
        String uniqueFileName = s3ClientService.generateUniqueFileName(originalFilename);
        //S3 키 생성
        String s3Key = s3ClientService.generateS3Key(fileCategory.name(), referenceId, uniqueFileName);

        //3. S3 업로드 및 FileMetadata 데이터 생성
        String filePath = null;
        try {
            //S3 업로드
            filePath = s3ClientService.uploadFile(file, s3Key);

            //entity 생성자 호출
            FileMetadata fileMetadata = FileMetadata.registerFile(fileCategory, referenceId,
                    file.getOriginalFilename(), uniqueFileName, file.getContentType(),
                    file.getOriginalFilename().substring(originalFilename.lastIndexOf('.') + 1),
                    file.getSize() / KILOBYTE_CONVERSION_CONSTANT, filePath);

            //FileMetaData 저장
            FileMetadata savedMetadata = fileMetadataRepository.save(fileMetadata);

            //DTO로 변환 후 반환
            return savedMetadata;

        } catch (Exception e) {
            //저장 실패시 S3에서 삭제
            s3ClientService.deleteFile(s3Key);
            throw new BusinessException(e.getMessage(), ErrorCode.FILE_UPLOAD_FAIL_ERROR);
        }
    }

    /**
     * 2-1. 파일 조회(단일)
     * 함수명 : getFile
     * @param fileCategory 파일 카테고리
     * @param referenceId 파일 참조 ID
     * @return FileMetadataDto 파일 메타데이터를 담은 응답 객체
     */
    @Transactional(readOnly = true)
    public FileMetadata getFile(FileCategory fileCategory, Long referenceId) {
        //해당 파일 카테고리와 참조 id로 entity를 가져온다.
        //파일이 존재하지 않아도 예외를 던지면 안됨.
        FileMetadata fileMetadataEntity = fileMetadataRepository.findByCategoryAndReferenceId(fileCategory, referenceId)
                .orElse(null);

        return fileMetadataEntity;
    }

    /**
     * 2-2. 파일 조회(리스트)
     * 함수명 : getFiles
     * @param fileCategory 파일 카테고리
     * @param referenceId 파일 참조 ID
     * @return List<FileMetadataDto> 파일 메타데이터 목록을 담은 응답 객체
     */
    @Transactional(readOnly = true)
    public List<FileMetadata> getFiles(FileCategory fileCategory, Long referenceId) {

        //해당 파일 카테고리와 참조 id로 entity를 가져온다.
        List<FileMetadata> fileMetadataEntities = fileMetadataRepository.findAllByCategoryAndReferenceId(fileCategory, referenceId);

        return fileMetadataEntities;
    }

    /**
     * 3. 파일 삭제(카테고리, 참조ID)
     * 함수명 : deleteFile
     * @param fileCategory 파일 카테고리
     * @param referenceId 참조 ID
     */
    @Transactional
    public void deleteFile(FileCategory fileCategory, Long referenceId) {
        //카테고리와 참조 ID로 검색후 존재 유무 판별
        FileMetadata toDeleteData = fileMetadataRepository.findByCategoryAndReferenceId(fileCategory, referenceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FILE_NOT_FOUND_ERROR));

        fileMetadataRepository.delete(toDeleteData);

        //TODO) 삭제 이력 남기기
    }

    /**
     * 3-1. 파일 삭제(파일메타데이터 id)
     * 함수명 : deleteFileById
     * @param fileId 파일메타데이터 id
     */
    @Transactional
    public void deleteFileById(Long fileId){
        //해당 파일 존재 유무 판별
        FileMetadata toDeleteData = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FILE_NOT_FOUND_ERROR));

        fileMetadataRepository.deleteById(toDeleteData.getId());

        //TODO) 삭제 이력 남기기
    }


    /**
     * 4. 파일 수정 (FileMetadata 테이블에서만 해시값 비교)
     * 함수명 : updateFilesWithHashComparison
     * @param fileCategory 파일 카테고리
     * @param referenceId 참조 ID
     * @param updatedFiles 클라이언트에서 전달받은 파일 목록
     */
    @Transactional
    public void updateFilesWithHashComparison(FileCategory fileCategory, Long referenceId, List<MultipartFile> updatedFiles) {
        // 1. 기존 파일 메타데이터 가져오기
        List<FileMetadata> existingFiles = fileMetadataRepository.findAllByCategoryAndReferenceId(fileCategory, referenceId);

        // 2. 기존 메타데이터가 없으면 모든 파일 업로드 처리
        if (existingFiles.isEmpty()) {
            for (MultipartFile file : updatedFiles) {
                uploadFile(file, fileCategory, referenceId);
            }
            return;
        }

        // 3. 기존 파일의 해시값 계산 (테이블 정보로만 비교) - key : 파일의 해시값, value : 파일
        Map<String, FileMetadata> existingFileHashMap = existingFiles.stream()
                .collect(Collectors.toMap(file -> calculateFileHashFromMetadata(file), file -> file));

        // 4. 클라이언트에서 전달된 파일의 해시값 계산 - key : 파일의 해시값, value : 파일
        Map<String, MultipartFile> updatedFileHashMap = updatedFiles.stream()
                .collect(Collectors.toMap(this::calculateFileHash, file -> file));

        // 5. 삭제 대상 파일 추출 (기존 파일 중, 클라이언트에 없는 해시값)
        List<FileMetadata> filesToDelete = existingFiles.stream()
                .filter(file -> !updatedFileHashMap.containsKey(calculateFileHashFromMetadata(file)))
                .collect(Collectors.toList());

        // 6. 추가 대상 파일 추출 (클라이언트 파일 중, 기존에 없는 해시값)
        List<MultipartFile> filesToAdd = updatedFiles.stream()
                .filter(file -> !existingFileHashMap.containsKey(calculateFileHash(file)))
                .collect(Collectors.toList());

        // 7. 삭제 대상 파일 처리
        for (FileMetadata fileToDelete : filesToDelete) {
            // 메타데이터 삭제
            fileMetadataRepository.delete(fileToDelete);
        }

        // 8. 추가 대상 파일 처리
        for (MultipartFile fileToAdd : filesToAdd) {
            // 업로드 로직 재활용
            uploadFile(fileToAdd, fileCategory, referenceId);
        }

        //TODO) 수정 이력 남기기
    }

    /**
     * FileMetadata 테이블 정보 기반 해시값 계산
     * @param file FileMetadata
     * @return SHA-256 해시값 (Base64 인코딩)
     */
    private String calculateFileHashFromMetadata(FileMetadata file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = file.getDisplayTitle() + file.getContentType() + file.getFileFormat() + file.getFileSize();
            byte[] hash = digest.digest(data.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), ErrorCode.FILE_METADATA_HASHING_FAILURE);
        }
    }

    /**
     * 클라이언트 파일에서 해시값 계산
     * @param file MultipartFile
     * @return SHA-256 해시값 (Base64 인코딩)
     */
    private String calculateFileHash(MultipartFile file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(file.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), ErrorCode.CLIENT_FILE_HASHING_FAILURE);
        }
    }


}
