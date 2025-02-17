package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckResult;
import com.seveneleven.project.dto.PostProjectChecklistAccept;
import com.seveneleven.project.dto.PostProjectChecklistReject;
import com.seveneleven.project.repository.CheckResultRepository;
import com.seveneleven.util.file.handler.LinkHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckResultStoreImpl implements CheckResultStore {

    private final CheckResultRepository checkResultRepository;
    private final CheckRequestLinkHistoryStore checkRequestLinkHistoryStore;
    private final LinkHandler linkHandler;

    @Override
    public PostProjectChecklistAccept.Response postApplicationAccept(
            CheckRequest checkRequest,
            Member member,
            String processorIp
    ) {
        CheckResult checkResult = CheckResult.accept(checkRequest, member, processorIp);
        checkResultRepository.save(checkResult);
        return PostProjectChecklistAccept.Response.toDto(checkResult);
    }

    @Override
    public PostProjectChecklistReject.Response postApplicationReject(
            CheckRequest checkRequest,
            Member member,
            String processorIp,
            PostProjectChecklistReject.Request requestDto
    ) {
        String rejectionReason = requestDto.getRejectReason();
        CheckResult checkResult = CheckResult.reject(checkRequest, member, processorIp, rejectionReason);
        checkResultRepository.save(checkResult);

//        //링크 추출
//        List<LinkPayload> linkPayloads = requestDto.getLinkInputs().stream()
//                .map(linkInput -> LinkPayload.toLinkPayload(
//                        LinkCategory.CHECK_REJECTION_REASON_LINK,
//                        savedResult.getId(),
//                        linkInput.getLinkTitle(),
//                        linkInput.getLink()
//                )).collect(Collectors.toList());
//
//        //링크 갯수 판별
//        if(linkPayloads.size() + linkHandler.countLinks(LinkCategory.CHECK_REJECTION_REASON_LINK, savedResult.getId()) > 10){
//            throw new BusinessException(ErrorCode.LINK_QUANTITY_EXCEED_ERROR);
//        }
//
//        //링크 리스트 업로드
//        for(LinkPayload linkPayload : linkPayloads) {
//            Link uploadedLink = linkHandler.uploadLink(linkPayload);
//
//            //이력 등록
//            checkRequestLinkHistoryStore.saveRejectionLinkUploadHistory(uploadedLink, member);
//        }

        return PostProjectChecklistReject.Response.toDto(checkResult);
    }
}
