package com.seveneleven.project.service;

import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.entity.project.Checklist;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.dto.GetChecklistApplication;
import com.seveneleven.project.service.checklist.CheckRequestReader;
import com.seveneleven.project.service.checklist.CheckResultReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectCheckRequestServiceImpl implements ProjectCheckRequestService {

    private final CheckRequestReader checkRequestReader;
    private final CheckResultReader checkResultReader;

    @Override
    public CheckRequest getCheckRequestById(Long id) {
        return checkRequestReader.read(id);
    }

    @Override
    public List<CheckRequest> getAllCheckRequestByChecklistId(Checklist checklist) {
        return checkRequestReader.readByChecklistId(checklist.getId());
    }

    @Override
    public List<GetChecklistApplication.ChecklistApplication> getChecklistApplications(List<CheckRequest> applications) {
        return applications.stream().map(application -> {
            try {
                return GetChecklistApplication.ChecklistApplication.toDto(application, checkResultReader.read(application.getId()));
            } catch (BusinessException e) {
                return GetChecklistApplication.ChecklistApplication.toDto(application);
            }
        }).toList();
    }
}
