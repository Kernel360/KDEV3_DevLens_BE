package com.seveneleven.company.service;

import com.seveneleven.company.repository.CompanyRepository;
import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.file.FileMetadataHistory;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.AdminMemberRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.repository.FileMetadataHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyFileHistoryServiceImpl implements CompanyFileHistoryService {
    private final AdminMemberRepository adminMemberRepository;
    private final CompanyRepository companyRepository;
    private final FileMetadataHistoryRepository fileMetadataHistoryRepository;

    /**
     * 1. 회사 로고 이미지 등록 이력 추가
     * 함수명 : registerLogoImageHistory
     * @param uploadedFileEntity 업로드된 파일메타데이터 엔티티
     * @param registrantId 등록 이력 수행자 id
     */
    @Override
    @Transactional
    public void registerLogoImageHistory(FileMetadata uploadedFileEntity, Long registrantId) {
        //1. 이력 등록자 정보 가져오기
        Member registrantEntity = adminMemberRepository.findById(registrantId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //2. 파일 업로더 정보 가져오기
        Member uploaderEntity = adminMemberRepository.findById(uploadedFileEntity.getCreatedBy())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //3. 로고 회사 정보 가져오기
        Company logoCompany = companyRepository.findById(uploadedFileEntity.getReferenceId())
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

        //4. 이력 엔티티 생성
        FileMetadataHistory uploadHistoryEntity = FileMetadataHistory.createRegisterHistory(
                uploadedFileEntity.getCategory(),
                uploadedFileEntity.getReferenceId(),
                logoCompany.getCompanyName(),
                uploadedFileEntity.getDisplayTitle(),
                uploadedFileEntity.getTitle(),
                uploaderEntity.getEmail(),
                uploaderEntity.getName(),
                uploaderEntity.getRole(),
                uploadedFileEntity.getCreatedAt(),
                uploadedFileEntity.getContentType(),
                uploadedFileEntity.getFileFormat(),
                uploadedFileEntity.getFileSize(),
                uploadedFileEntity.getFilePath(),
                registrantEntity.getName(),
                registrantEntity.getEmail(),
                registrantEntity.getRole()
        );

        //5. 이력 엔티티 저장
        fileMetadataHistoryRepository.save(uploadHistoryEntity);
    }

    /**
     * 2. 회사 로고 이미지 삭제 이력 추가
     * 함수명 : deleteLogoImageHistory
     * @param deletedFileEntity 삭제된 로고 이미지 파일메타데이터 엔티티
     * @param registrantId 삭제 이력 수행자 id
     */
    @Override
    @Transactional
    public void deleteLogoImageHistory(FileMetadata deletedFileEntity, Long registrantId) {
        //1. 이력 등록자 정보 가져오기
        Member registrantEntity = adminMemberRepository.findById(registrantId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //2. 파일 업로더 정보 가져오기
        Member uploaderEntity = adminMemberRepository.findById(deletedFileEntity.getCreatedBy())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //3. 로고 회사 정보 가져오기
        Company logoCompany = companyRepository.findById(deletedFileEntity.getReferenceId())
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

        //4. 이력 엔티티 생성
        FileMetadataHistory deleteHistoryEntity = FileMetadataHistory.createDeleteHistory(
                deletedFileEntity.getCategory(),
                deletedFileEntity.getReferenceId(),
                logoCompany.getCompanyName(),
                deletedFileEntity.getDisplayTitle(),
                deletedFileEntity.getTitle(),
                uploaderEntity.getEmail(),
                uploaderEntity.getName(),
                uploaderEntity.getRole(),
                deletedFileEntity.getCreatedAt(),
                deletedFileEntity.getContentType(),
                deletedFileEntity.getFileFormat(),
                deletedFileEntity.getFileSize(),
                deletedFileEntity.getFilePath(),
                registrantEntity.getName(),
                registrantEntity.getEmail(),
                registrantEntity.getRole()
        );

        //5. 이력 엔티티 저장
        fileMetadataHistoryRepository.save(deleteHistoryEntity);
    }

}
