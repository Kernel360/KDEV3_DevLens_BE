package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.PostProjectChecklist;
import com.seveneleven.project.dto.PutProjectChecklist;
import com.seveneleven.project.repository.ChecklistRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChecklistStoreImpl implements ChecklistStore {

    private final ChecklistRepository checklistRepository;
    private final ProjectStepRepository projectStepRepository;

    @Override
    public Checklist storeChecklist(ProjectStep projectStep, PostProjectChecklist.Request request) {
        return checklistRepository.save(request.toEntity(projectStep));
    }

    @Override
    public Checklist updateChecklist(Checklist checklist, PutProjectChecklist.Request request) {
        return checklist.updateChecklist(request.getTitle(), request.getDescription());
    }

    @Override
    public void accept(Checklist checklist) {
        checklist.acceptChecklist();
    }

    @Override
    public void delete(Checklist checklist) {
        checklistRepository.save(checklist.deleteChecklist());
    }

    @Override
    public void deleteAll(List<Checklist> checklists) {
        checklists.forEach(this::delete);
    }
}
