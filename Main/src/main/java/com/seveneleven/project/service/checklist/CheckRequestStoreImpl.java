package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckRequestHistory;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.project.dto.PostProjectChecklistApplication;
import com.seveneleven.project.repository.CheckRequestRepository;
import com.seveneleven.util.GetIpUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckRequestStoreImpl implements CheckRequestStore {

    private final CheckRequestRepository checkRequestRepository;
    private final GetIpUtil getIpUtil;

    @Override
    @Transactional
    public CheckRequest checkRequestStore(
            Checklist checklist,
            PostProjectChecklistApplication.Request requestDto,
            Member member,
            String ipAddress
    ) {
        CheckRequest checkRequest = requestDto.createCheckRequest(checklist, member, ipAddress);
        return checkRequestRepository.save(checkRequest);
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
