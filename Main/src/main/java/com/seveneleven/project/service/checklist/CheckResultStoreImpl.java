package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.CheckResult;
import com.seveneleven.project.dto.PostProjectChecklistAccept;
import com.seveneleven.project.dto.PostProjectChecklistReject;
import com.seveneleven.project.repository.CheckResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckResultStoreImpl implements CheckResultStore {

    private final CheckResultRepository checkResultRepository;

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
            String rejectionReason
    ) {
        CheckResult checkResult = CheckResult.reject(checkRequest, member, processorIp, rejectionReason);
        checkResultRepository.save(checkResult);
        return PostProjectChecklistReject.Response.toDto(checkResult);
    }
}
