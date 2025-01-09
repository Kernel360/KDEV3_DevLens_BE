package com.seveneleven.devlens.global.util.file.Service;

import com.seveneleven.devlens.domain.admin.db.CompanyRepository;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.global.exception.BusinessException;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.ErrorCode;
import com.seveneleven.devlens.global.response.SuccessCode;
import com.seveneleven.devlens.global.util.file.constant.FileCategory;
import com.seveneleven.devlens.global.util.file.dto.FileMetadataDto;
import com.seveneleven.devlens.global.util.file.entity.FileMetadata;
import com.seveneleven.devlens.global.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyFileService {
    private final FileService fileService;
    private final CompanyRepository companyRepository;
    private final FileMetadataRepository fileMetadataRepository;

    /**
     * 1. 회사 로고 이미지 등록
     * @auth admin, super(해당 회사 대표회원)
     * @param file 업로드할 로고 이미지 파일
     * @param companyId 해당 회사 id
     * @param uploaderId 업로드 수행자 id
     * @return APIResponse S3에 저장된 파일의 메타데이터 response
     */

    @Transactional
    public APIResponse uploadLogoImage(MultipartFile file, Long companyId, Long uploaderId) throws Exception{
        //1. 회사 id로 존재여부 판별
        Company companyEntity = companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND));

        //2. 프로필 등록 상태인지 판별
        if(YN.Y.equals(companyEntity.getRepresentativeImageExists())){
            throw new BusinessException(ErrorCode.LOGO_ALREADY_EXIST_ERROR);
        }

        //TODO) 2. 수행자 권한 판별 validation - admin판별, super의 회사 판별

        //3. S3파일 업로드, 메타데이터 테이블 저장
        APIResponse uploadResponse = fileService.uploadFile(file, "COMPANY_LOGO_IMAGE", companyId);

        //4. 회사 대표 이미지 유무 칸을 Y로 변경한다.
        companyEntity.addRepresentativeImage();
        companyRepository.save(companyEntity);

        //5. 반환
        return uploadResponse;
    }

    /**
     * 2. 회사 로고 이미지 조회
     * @auth admin, super(해당 회사 대표회원)
     * @param companyId 해당 회사 id
     * @return APIResponse S3에 저장된 파일의 메타데이터 response
     */
    @Transactional(readOnly = true)
    public APIResponse getLogoImage(Long companyId) throws Exception{
        //회사 유효성 검사
        if(!companyRepository.existsById(companyId)){
            throw new BusinessException(ErrorCode.COMPANY_IS_NOT_FOUND);
        }

        //카테고리와 참조 id 로 FileMetadata 탐색
        FileMetadataDto fileDto = fileService.getFile("COMPANY_LOGO_IMAGE", companyId);

        return APIResponse.success(SuccessCode.OK, fileDto);
    }


    /**
     * 3. 회사 로고 이미지 삭제
     * @auth admin, super(해당 회사 대표회원)
     * @param fileId 해당 파일 id
     * @return APIResponse S3에 저장된 파일의 메타데이터 response
     */
    @Transactional
    public APIResponse deleteLogo(Long fileId) throws Exception{
        //fileId로 해당 파일 존재 유무 판별
        FileMetadata fileMetadata = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FILE_NOT_FOUND_ERROR));

        //TODO) 2. 수행자 권한 판별 validation - admin판별, super의 회사 판별

        //삭제
        APIResponse deleteResponse = fileService.deleteFile(fileId);

        return deleteResponse;
    }
}

