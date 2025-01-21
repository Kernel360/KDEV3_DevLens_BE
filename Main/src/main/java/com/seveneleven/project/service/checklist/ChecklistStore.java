package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.project.dto.PostProjectChecklist;
import com.seveneleven.project.dto.PutProjectChecklist;

import java.util.List;

public interface ChecklistStore {
    Checklist storeChecklist(PostProjectChecklist.Request checklist);
    Checklist updateChecklist(PutProjectChecklist.Request request);
    void accept(Checklist checklist);
    void delete(Checklist checklist);
    void deleteAll(List<Checklist> checklists);
}
