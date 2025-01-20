package com.seveneleven.project.service.checklist;

import com.seveneleven.entity.project.Checklist;
import com.seveneleven.project.dto.PostProjectChecklist;
import com.seveneleven.project.dto.PutProjectChecklist;

public interface ChecklistStore {
    Checklist storeChecklist(PostProjectChecklist.Request checklist);
    Checklist updateChecklist(PutProjectChecklist.Request request);
    void accept(Checklist checklist);
}
