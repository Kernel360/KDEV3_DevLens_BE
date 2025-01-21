package com.seveneleven.member.service;

import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.entity.member.Member;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.Service.FileService;
import com.seveneleven.util.file.dto.FileMetadataDto;
import com.seveneleven.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberFileService {
    private final FileService fileService;
    private final MemberRepository memberRepository;
    private final FileMetadataRepository fileMetadataRepository;

    /**
     * 함수명 : uploadProfileImage
     * 1. 계정 프로필 이미지 등록
     * @auth admin, user(해당 회원)
     * @param file 업로드할 프로필 이미지 파일
     * @param memberId 해당 회원 id
     * @param userDetails 현재 수행자 정보
     */
    @Transactional
    public void uploadProfileImage(MultipartFile file, Long memberId, Long uploaderId) {
        //1. memberId로 존재 여부 판별
        Member memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //TODO) 2. 수행자 권한 판별

        //3. 프로필 이미지 존재하는지 판별
        if(fileMetadataRepository.existsByCategoryAndReferenceId(FileCategory.USER_PROFILE_IMAGE, memberId)){
            throw new BusinessException(ErrorCode.PROFILE_IMAGE_ALREADY_EXIST);
        }

        //4. S3 파일 업로드, 메타데이터 테이블 저장
        fileService.uploadFile(file, FileCategory.USER_PROFILE_IMAGE, memberId);
    }

    /**
     * 함수명 : getProfileImage
     * 2. 계정 프로필 이미지 조회
     * @param memberId 해당 회원 id
     * @return fileMetadataDto S3에 저장된 파일의 메타데이터 DTO
     */
    @Transactional(readOnly = true)
    public FileMetadataDto getProfileImage(Long memberId) {
        //회원 유효성 검사
        if(!memberRepository.existsById(memberId)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        //카테고리와 참조id로 filemetadata 검색
        FileMetadata fileEntity = fileService.getFile(FileCategory.USER_PROFILE_IMAGE, memberId);

        return FileMetadataDto.toDto(fileEntity);
    }

    /**
     * 함수명 : deleteProfileImage
     * 3. 계정 프로필 이미지 삭제
     * @auth admin, 해당 user
     * @param memberId 해당 회원 id
     */
    @Transactional
    public void deleteProfileImage(Long memberId, Long uploaderId) {
        //1. 회원 존재 유무 판별
        Member memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //TODO)2. 수행자 ID 정보 판별

        //3. 삭제 수행
        fileService.deleteFile(FileCategory.USER_PROFILE_IMAGE, memberId);

        //TODO) 4. 삭제 이력 추가

    }
}
