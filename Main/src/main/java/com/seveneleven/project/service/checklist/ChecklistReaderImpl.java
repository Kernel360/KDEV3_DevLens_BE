package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.dto.GetStepChecklist;
import com.seveneleven.project.repository.ChecklistRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChecklistReaderImpl implements ChecklistReader {

    private final ChecklistRepository checklistRepository;

    public Checklist getChecklist(Long checklistId) {
        return checklistRepository.findByIdAndIsActive(
                checklistId, YesNo.YES
        ).orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_NOT_FOUND));
    }

    @Override
    public GetStepChecklist.Response getStepChecklist(Long stepId) {
        return new GetStepChecklist.Response(stepId, checklistRepository.findStepChecklist(stepId));
    }
}
