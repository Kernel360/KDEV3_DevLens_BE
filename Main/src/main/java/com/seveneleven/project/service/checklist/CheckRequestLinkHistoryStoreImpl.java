package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.file.Link;
import com.seveneleven.entity.file.LinkHistory;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.repository.CheckRequestRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.repository.LinkHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckRequestLinkHistoryStoreImpl implements CheckRequestLinkHistoryStore {

    private final MemberRepository memberRepository;
    private final CheckRequestRepository checkRequestRepository;
    private final LinkHistoryRepository linkHistoryRepository;

    /**
     * 1. 체크 요청 링크 등록 이력 저장
     * 함수명 : saveRequestLinkUploadHistory
     * @param uploadedLinkEntity 업로드된 링크 엔티티
     * @param registrant 등록 이력 수행자
     */
    @Override
    @Transactional
    public void saveRequestLinkUploadHistory(Link uploadedLinkEntity, Member registrant) {
        //1. 링크 업로더 정보 가져오기
        Member uploaderEntity = memberRepository.findById(uploadedLinkEntity.getCreatedBy())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //2. 체크 요청 정보 가져오기
        CheckRequest checkRequest = checkRequestRepository.findById(uploadedLinkEntity.getReferenceId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECK_REQUEST_NOT_FOUND));

        //3. 이력 엔티티 생성
        LinkHistory uploadHistoryEntity = LinkHistory.createRegisterHistory(
                uploadedLinkEntity.getCategory(),
                uploadedLinkEntity.getReferenceId(),
                checkRequest.getTitle(),
                uploaderEntity.getEmail(),
                uploaderEntity.getName(),
                uploaderEntity.getRole(),
                uploadedLinkEntity.getCreatedAt(),
                uploadedLinkEntity.getLinkTitle(),
                uploadedLinkEntity.getLink(),
                registrant.getName(),
                registrant.getEmail(),
                registrant.getRole()
        );

        //4. 이력 엔티티 저장
        linkHistoryRepository.save(uploadHistoryEntity);
    }

    /**
     * 2. 체크 요청 반려 링크 등록
     * 함수명 : saveRejectionLinkUploadHistory
     * @param uploadedLinkEntity 업로드된 링크 엔티티
     * @param registrant 등록 이력 수행자
     */
    @Override
    @Transactional
    public void saveRejectionLinkUploadHistory(Link uploadedLinkEntity, Member registrant) {
        //1. 링크 업로더 정보 가져오기
        Member uploaderEntity = memberRepository.findById(uploadedLinkEntity.getCreatedBy())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        //2. 체크 요청 정보 가져오기
        CheckRequest checkRequest = checkRequestRepository.findById(uploadedLinkEntity.getReferenceId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECK_REQUEST_NOT_FOUND));

        //3. 이력 엔티티 생성
        LinkHistory uploadHistoryEntity = LinkHistory.createRegisterHistory(
                uploadedLinkEntity.getCategory(),
                uploadedLinkEntity.getReferenceId(),
                checkRequest.getTitle(),
                uploaderEntity.getEmail(),
                uploaderEntity.getName(),
                uploaderEntity.getRole(),
                uploadedLinkEntity.getCreatedAt(),
                uploadedLinkEntity.getLinkTitle(),
                uploadedLinkEntity.getLink(),
                registrant.getName(),
                registrant.getEmail(),
                registrant.getRole()
        );

        //4. 이력 엔티티 저장
        linkHistoryRepository.save(uploadHistoryEntity);
    }
}
