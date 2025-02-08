package com.seveneleven.board.service;

import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.file.Link;
import com.seveneleven.entity.file.LinkHistory;
import com.seveneleven.entity.member.Member;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.repository.LinkHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLinkHistoryService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LinkHistoryRepository linkHistoryRepository;

    /**
     * 1. 게시물 링크 등록 이력 추가
     * 함수명 : registerPostLinkHistory
     * @param uploadedLinkEntity 업로드된 링크 엔티티
     * @param registrantId 등록 수행자 id
     */
    @Transactional
    public void registerPostLinkHistory(Link uploadedLinkEntity, Long registrantId){
        //1. 이력 등록자 정보 가져오기
        Member registrantEntity = memberRepository.findById(registrantId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //2. 링크 업로더 정보 가져오기
        Member uploaderEntity = memberRepository.findById(uploadedLinkEntity.getCreatedBy())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //3. 게시물 정보 가져오기
        Post postEntity = postRepository.findById(uploadedLinkEntity.getReferenceId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //4. 이력 엔티티 생성
        LinkHistory uploadHistoryEntity = LinkHistory.createRegisterHistory(
                uploadedLinkEntity.getCategory(),
                uploadedLinkEntity.getReferenceId(),
                postEntity.getTitle(),
                uploaderEntity.getEmail(),
                uploaderEntity.getName(),
                uploaderEntity.getRole(),
                uploadedLinkEntity.getCreatedAt(),
                uploadedLinkEntity.getLinkTitle(),
                uploadedLinkEntity.getLink(),
                registrantEntity.getName(),
                registrantEntity.getEmail(),
                registrantEntity.getRole()
        );

        //5. 이력 엔티티 저장
        linkHistoryRepository.save(uploadHistoryEntity);
    }

    /**
     * 2. 게시물 링크 삭제 이력 추가
     * 함수명 : deletePostLinkHistory
     * @param deletedLinkEntity 삭제된 링크 엔티티
     * @param registrantId 삭제 수행자 id
     */
    @Transactional
    public void deletePostLinkHistory(Link deletedLinkEntity, Long registrantId) {
        //1. 이력 등록자 정보 가져오기
        Member registrantEntity = memberRepository.findById(registrantId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //2. 링크 업로더 정보 가져오기
        Member uploaderEntity = memberRepository.findById(deletedLinkEntity.getCreatedBy())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //3. 게시물 정보 가져오기
        Post postEntity = postRepository.findById(deletedLinkEntity.getReferenceId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //4. 이력 엔티티 생성
        LinkHistory uploadHistoryEntity = LinkHistory.createDeleteHistory(
                deletedLinkEntity.getCategory(),
                deletedLinkEntity.getReferenceId(),
                postEntity.getTitle(),
                uploaderEntity.getEmail(),
                uploaderEntity.getName(),
                uploaderEntity.getRole(),
                deletedLinkEntity.getCreatedAt(),
                deletedLinkEntity.getLinkTitle(),
                deletedLinkEntity.getLink(),
                registrantEntity.getName(),
                registrantEntity.getEmail(),
                registrantEntity.getRole()
        );

        //5. 이력 엔티티 저장
        linkHistoryRepository.save(uploadHistoryEntity);
    }
}
