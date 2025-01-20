package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckRequestHistory;
import com.seveneleven.project.dto.PostProjectChecklistApplication;
import com.seveneleven.project.repository.CheckRequestRepository;
import com.seveneleven.util.GetIpUtil;
import jakarta.servlet.http.HttpServletRequest;
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
            PostProjectChecklistApplication.Request requestDto,
            Member member,
            HttpServletRequest request
    ) {
        // TODO - 파일, 링크 처리 필요.
        // TODO - 회원 파트 서비스 호출로 변경
        String ipAddress = getIpUtil.getIpAddress(request);

        CheckRequest checkRequest = requestDto.createCheckRequest(member, ipAddress);
        return checkRequestRepository.save(checkRequest);
    }

    @Override
    public CheckRequestHistory checkRequestHistoryStore(CheckRequest checkRequest) {
        return checkRequest.createCheckRequestHistory();
    }

    @Override
    public void acceptCheckRequest(CheckRequest checkRequest) {
        checkRequest.accept();
    }

    @Override
    public void rejectCheckRequest(CheckRequest checkRequest) {
        checkRequest.reject();
    }
}
