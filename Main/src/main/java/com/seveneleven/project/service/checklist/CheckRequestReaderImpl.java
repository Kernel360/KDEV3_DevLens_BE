package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.repository.CheckRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.seveneleven.response.ErrorCode.CHECK_REQUEST_NOT_FOUND;

@Component
@Slf4j
@RequiredArgsConstructor
public class CheckRequestReaderImpl implements CheckRequestReader {

    private final CheckRequestRepository checkRequestRepository;

    @Override
    public CheckRequest read(Long applicationId) {
        return checkRequestRepository.findByIdAndIsActive(applicationId, YesNo.YES)
                .orElseThrow(() -> new BusinessException(CHECK_REQUEST_NOT_FOUND));
    }

    @Override
    public List<CheckRequest> readByChecklistId(Long checklistId) {
        return checkRequestRepository.findByChecklistIdAndIsActive(checklistId, YesNo.YES);
    }
}
