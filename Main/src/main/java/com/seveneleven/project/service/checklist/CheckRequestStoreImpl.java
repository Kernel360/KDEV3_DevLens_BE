package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.file.constant.LinkCategory;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckRequestHistory;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.dto.PostProjectChecklistApplication;
import com.seveneleven.project.repository.CheckRequestRepository;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.GetIpUtil;
import com.seveneleven.util.file.Service.LinkService;
import com.seveneleven.util.file.dto.LinkInput;
import com.seveneleven.util.file.dto.LinkPayload;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckRequestStoreImpl implements CheckRequestStore {

    private final CheckRequestRepository checkRequestRepository;
    private final GetIpUtil getIpUtil;
    private final LinkService linkService;

    @Override
    @Transactional
    public CheckRequest checkRequestStore(
            Checklist checklist,
            PostProjectChecklistApplication.Request requestDto,
            Member member,
            String ipAddress
    ) {
        CheckRequest checkRequest = requestDto.createCheckRequest(checklist, member, ipAddress);
        //저장한 엔티티 추출
        CheckRequest savedRequest = checkRequestRepository.save(checkRequest);

        //링크 추출
        List<LinkPayload> linkPayloads = requestDto.getLinkInputs().stream()
                .map(linkInput -> LinkPayload.toLinkPayload(
                        LinkCategory.CHECK_APPROVE_REQUEST_LINK,
                        savedRequest.getId(),
                        linkInput.getLinkTitle(),
                        linkInput.getLink()
                )).collect(Collectors.toList());

        //링크 갯수 판별
        if(linkPayloads.size() + linkService.countLinks(LinkCategory.CHECK_APPROVE_REQUEST_LINK, savedRequest.getId()) > 10){
            throw new BusinessException(ErrorCode.LINK_QUANTITY_EXCEED_ERROR);
        }

        //링크 리스트 업로드
        for(LinkPayload linkPayload : linkPayloads) {
            linkService.uploadLink(linkPayload);
        }

        return checkRequest;
    }

    @Override
    public CheckRequestHistory checkRequestHistoryStore(CheckRequest checkRequest) {
        return checkRequest.createCheckRequestHistory();
    }

    @Override
    public CheckRequest acceptCheckRequest(CheckRequest checkRequest) {
        return checkRequestRepository.save(checkRequest.accept());
    }

    @Override
    public CheckRequest rejectCheckRequest(CheckRequest checkRequest) {
        return checkRequestRepository.save(checkRequest.reject());
    }
}
