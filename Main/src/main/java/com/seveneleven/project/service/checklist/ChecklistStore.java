package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.PostProjectChecklist;
import com.seveneleven.project.dto.PutProjectChecklist;

import java.util.List;

public interface ChecklistStore {
    Checklist storeChecklist(ProjectStep projectStep, PostProjectChecklist.Request checklist);
    Checklist updateChecklist(Checklist checklist, PutProjectChecklist.Request request);
    void accept(Checklist checklist);
    void delete(Checklist checklist);
    void deleteAll(List<Checklist> checklists);
}
