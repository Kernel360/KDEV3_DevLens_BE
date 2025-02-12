package com.seveneleven.board.service;

import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.file.Link;
import com.seveneleven.entity.file.constant.LinkCategory;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.handler.LinkHandler;
import com.seveneleven.util.file.dto.LinkInput;
import com.seveneleven.util.file.dto.LinkPayload;
import com.seveneleven.util.file.dto.LinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLinkServiceImpl implements PostLinkService {
    private final LinkHandler linkHandler;
    private final PostRepository postRepository;
    private final PostLinkHistoryServiceImpl postLinkHistoryServiceImpl;

    private static final int MAX_LINK_COUNT = 10; //게시물별 최대 링크 수(10개)

    /**
     * 1. 게시물 링크 일괄 업로드
     * 함수명 : uploadPostLinks
     * @auth admin, 게시물 작성자
     * @param linkInputs 업로드할 링크 DTO 목록
     * @param postId 해당 게시물 id
     * @param uploaderId 업로드 수행자 id
     */
    @Override
    @Transactional
    public void uploadPostLinks(List<LinkInput> linkInputs, Long postId, Long uploaderId) {
        //1. 게시물 id로 존재여부 판별
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //TODO) 2. 수행자 판별 - admin 해당 게시글 작성자

        //3. 현재 저장된 링크 갯수 확인(저장할 링크 갯수 + 현재 저장 갯수 > 10인지 판별)
        Integer currentLinkCnt = linkHandler.countLinks(LinkCategory.POST_ATTACHMENT_LINK, postEntity.getId());
        if(currentLinkCnt + linkInputs.size() >= MAX_LINK_COUNT){
            throw new BusinessException(ErrorCode.LINK_QUANTITY_EXCEED_ERROR);
        }

        //4. LinkInputs <-> LinkPayloads 변환
        List<LinkPayload> linkPayloads = linkInputs.stream()
                .map(linkInput -> LinkPayload.toLinkPayload(
                        LinkCategory.POST_ATTACHMENT_LINK,
                        postEntity.getId(),
                        linkInput.getLinkTitle(),
                        linkInput.getLink()
                )).collect(Collectors.toList());

        //4. 링크 리스트 업로드
        for(LinkPayload linkPayload : linkPayloads){
            Link uploadedLinkEntity = linkHandler.uploadLink(linkPayload);
            //링크 업로드 이력 등록
            postLinkHistoryServiceImpl.registerPostLinkHistory(uploadedLinkEntity, uploaderId);
        }
    }

    /**
     * 2. 게시물 링크 조회
     * 함수명 : getPostLinks
     * @param postId 해당 게시물 id
     * @return List<LinkResponse> 해당 게시물의 링크 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<LinkResponse> getPostLinks(Long postId) {
        //1. 게시물 id로 존재여부 판별
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //2. 게시물의 링크 엔티티 가져오기
        //페이지네이션 없음
        List<Link> linkEntities = linkHandler.getLinks(LinkCategory.POST_ATTACHMENT_LINK, postEntity.getId());

        //3. 게시물의 링크 엔티티를 dto로 변환하기
        return Optional.ofNullable(linkEntities)
                .orElse(List.of())
                .stream()
                .map(LinkResponse::toDto)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    /**
     * 3. 게시물 단일 삭제
     * 함수명 : deletePostLink
     * 게시글 수정시 링크 수정용으로 사용
     * @param linkId 삭제할 링크 id
     */
    @Override
    @Transactional
    public void deletePostLink(Long postId, Long linkId, Long deleterId){
        //1. 게시물 존재 여부 판별
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //TODO) 2. 수행자 권한 검증

        //3. 해당 게시물의 링크인지 판별
        //파일 메타데이터 테이블에서 해당 카테고리와 첨부 id로 메타데이터 id 목록 생성
        List<Link> linkEntities = linkHandler.getLinks(LinkCategory.POST_ATTACHMENT_LINK, postId);
        List<Long> linkIds = linkEntities.stream().map(Link::getId).collect(Collectors.toList());
        //id 목록에 linkId가 있는지 판별
        if(!linkIds.contains(linkId)){
            throw new BusinessException(ErrorCode.LINKID_NOT_EXIST_ERROR);
        }

        //3. 게시물 링크 삭제
        Link deletedLink = linkHandler.deleteLinkById(linkId);

        //4. 삭제 이력 등록
        postLinkHistoryServiceImpl.deletePostLinkHistory(deletedLink, deleterId);
    }

    /**
     * 3-1. 게시물 링크 일괄 삭제
     * 함수명 : deleteAllPostLinks
     * @param postId 해당 게시물 id
     */
    @Override
    @Transactional
    public void deleteAllPostLinks(Long postId) {
        //1. 게시물 유효성 검사
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //TODO) 2. 수행자 권한 판별

        //3. 해당 게시물의 링크를 모두 삭제한다.
        for(Link linkEntity : linkHandler.getLinks(LinkCategory.POST_ATTACHMENT_LINK, postEntity.getId())){
            Link deletedLink = linkHandler.deleteLinkById(linkEntity.getId());
            //삭제 이력 등록
            postLinkHistoryServiceImpl.deletePostLinkHistory(deletedLink, postId);
        }
    }

}
