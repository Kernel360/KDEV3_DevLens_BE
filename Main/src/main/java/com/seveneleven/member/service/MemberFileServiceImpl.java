package com.seveneleven.member.service;

import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.entity.member.Member;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.handler.FileHandler;
import com.seveneleven.util.file.dto.FileMetadataResponse;
import com.seveneleven.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberFileServiceImpl implements MemberFileService {
    private final FileHandler fileHandler;
    private final MemberRepository memberRepository;
    private final FileMetadataRepository fileMetadataRepository;
    private final MemberFileHistoryService memberFileHistoryService;

    /**
     * 함수명 : uploadProfileImage
     * 1. 계정 프로필 이미지 등록
     * 이미지를 다시 등록할 경우 해당 이미지로 교체된다.
     * @auth admin, user(해당 회원)
     * @param file 업로드할 프로필 이미지 파일
     * @param memberId 해당 회원 id
     * @param uploaderId 현재 수행자 정보
     */
    @Override
    @Transactional
    public void uploadProfileImage(MultipartFile file, Long memberId, Long uploaderId) {
        //1. memberId로 존재 여부 판별
        Member memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //TODO) 2. 수행자 권한 판별

        //3. 프로필 이미지 존재하는지 판별
        if(fileMetadataRepository.existsByCategoryAndReferenceId(FileCategory.USER_PROFILE_IMAGE, memberEntity.getId())){
            //기존 프로필 이미지 파일을 삭제한다.
            //3-1. 삭제 수행
            FileMetadata deletedFile = fileHandler.deleteFile(FileCategory.USER_PROFILE_IMAGE, memberEntity.getId());
            //3-2. 삭제 이력 등록
            memberFileHistoryService.deleteLogoImageHistory(deletedFile, uploaderId);

            //새 로고 이미지를 등록한다.
            //3-3. S3 파일 업로드, 메타데이터 테이블 저장
            FileMetadata uploadedFileEntity = fileHandler.uploadFile(file, FileCategory.USER_PROFILE_IMAGE, memberEntity.getId());
            //3-4. 업로드 이력 등록
            memberFileHistoryService.registerProfileImageHistory(uploadedFileEntity, uploaderId);

            return;
        }

        //4. S3 파일 업로드, 메타데이터 테이블 저장
        FileMetadata uploadedFileEntity = fileHandler.uploadFile(file, FileCategory.USER_PROFILE_IMAGE, memberEntity.getId());

        //5. 업로드 이력 등록
        memberFileHistoryService.registerProfileImageHistory(uploadedFileEntity, uploaderId);
    }

    /**
     * 함수명 : getProfileImage
     * 2. 계정 프로필 이미지 조회
     * @param memberId 해당 회원 id
     * @return fileMetadataDto S3에 저장된 파일의 메타데이터 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public FileMetadataResponse getProfileImage(Long memberId) {
        //1. memberId로 존재 여부 판별
        Member memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //카테고리와 참조id로 filemetadata 검색
        FileMetadata fileEntity = fileHandler.getFile(FileCategory.USER_PROFILE_IMAGE, memberEntity.getId());

        return FileMetadataResponse.toDto(fileEntity).orElse(null);
    }

    /**
     * 함수명 : deleteProfileImage
     * 3. 계정 프로필 이미지 삭제
     * @auth admin, 해당 user
     * @param memberId 해당 회원 id
     * @param deleterId 삭제 수행자 id
     */
    @Override
    @Transactional
    public void deleteProfileImage(Long memberId, Long deleterId) {
        //1. 회원 존재 유무 판별
        Member memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //TODO)2. 수행자 ID 정보 판별

        //3. 삭제 수행
        FileMetadata deletedFile = fileHandler.deleteFile(FileCategory.USER_PROFILE_IMAGE, memberEntity.getId());

        //4. 삭제 이력 등록
        memberFileHistoryService.deleteLogoImageHistory(deletedFile, deleterId);
    }
}
