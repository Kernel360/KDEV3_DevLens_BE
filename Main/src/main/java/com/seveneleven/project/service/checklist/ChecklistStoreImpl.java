package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.dto.PostProjectChecklist;
import com.seveneleven.project.dto.PutProjectChecklist;
import com.seveneleven.project.repository.ChecklistRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import com.seveneleven.response.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChecklistStoreImpl implements ChecklistStore {

    private final ChecklistRepository checklistRepository;
    private final ProjectStepRepository projectStepRepository;

    @Override
    public Checklist storeChecklist(PostProjectChecklist.Request request) {
        return checklistRepository.save(request.toEntity(
                projectStepRepository.findByIdAndIsActive(
                        request.getProjectStepId(),
                        YesNo.YES
                ).orElseThrow(EntityNotFoundException::new)));
    }

    @Override
    @Transactional
    public Checklist updateChecklist(PutProjectChecklist.Request request) {
        return checklistRepository.findById(request.getChecklistId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_NOT_FOUND))
                .updateChecklist(request.getTitle(), request.getDescription());
    }

    @Override
    public void accept(Checklist checklist) {
        checklist.acceptChecklist();
    }
}
