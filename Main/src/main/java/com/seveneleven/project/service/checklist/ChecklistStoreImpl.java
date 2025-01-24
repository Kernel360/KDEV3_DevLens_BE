package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.dto.PostProjectChecklist;
import com.seveneleven.project.dto.PutProjectChecklist;
import com.seveneleven.project.repository.ChecklistRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                ).orElseThrow(() -> new BusinessException(ErrorCode.CHECKLIST_NOT_FOUND))));
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

    @Override
    public void delete(Checklist checklist) {
        checklist.deleteChecklist();
    }

    @Override
    public void deleteAll(List<Checklist> checklists) {
        checklists.forEach(this::delete);
    }
}
