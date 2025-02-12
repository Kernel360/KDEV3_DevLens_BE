package com.seveneleven.board.service;

import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.file.FileMetadataHistory;
import com.seveneleven.entity.member.Member;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.repository.FileMetadataHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostFileHistoryServiceImpl implements PostFileHistoryService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final FileMetadataHistoryRepository fileMetadataHistoryRepository;


    /**
     * 1. 게시물 파일 등록 이력 추가
     * 함수명 : registerPostFileHistory
     * @param uploadedFileEntity 업로드된 파일 파일메타데이터 엔티티
     * @param registrantId 등록 수행자 id
     */
    @Override
    @Transactional
    public void registerPostFileHistory(FileMetadata uploadedFileEntity, Long registrantId){
        //1. 이력 등록자 정보 가져오기
        Member registrantEntity = memberRepository.findById(registrantId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //2. 파일 업로더 정보 가져오기
        Member uploaderEntity = memberRepository.findById(uploadedFileEntity.getCreatedBy())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //3. 게시물 정보 가져오기
        Post postEntity = postRepository.findById(uploadedFileEntity.getReferenceId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //4. 이력 엔티티 생성
        FileMetadataHistory uploadHistoryEntity = FileMetadataHistory.createRegisterHistory(
                uploadedFileEntity.getCategory(),
                uploadedFileEntity.getReferenceId(),
                postEntity.getTitle(),
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
     * 2. 게시물 파일 삭제 이력 추가
     * 함수명 : deletePostFileHistory
     * @param deletedFileEntity 삭제된 파일 파일메타데이터 엔티티
     * @param registrantId 삭제 수행자 id
     */
    @Override
    @Transactional
    public void deletePostFileHistory(FileMetadata deletedFileEntity, Long registrantId){
        //1. 이력 등록자 정보 가져오기
        Member registrantEntity = memberRepository.findById(registrantId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //2. 파일 업로더 정보 가져오기
        Member uploaderEntity = memberRepository.findById(deletedFileEntity.getCreatedBy())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //3. 게시물 정보 가져오기
        Post postEntity = postRepository.findById(deletedFileEntity.getReferenceId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //4. 삭제 이력 엔티티 생성
        FileMetadataHistory deleteHistoryEntity = FileMetadataHistory.createDeleteHistory(
                deletedFileEntity.getCategory(),
                deletedFileEntity.getReferenceId(),
                postEntity.getTitle(),
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
