package com.seveneleven.project.service;

import com.seveneleven.entity.project.CheckRequest;
import com.seveneleven.project.service.checklist.CheckRequestReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectCheckRequestServiceImpl implements ProjectCheckRequestService {

    private final CheckRequestReader checkRequestReader;

    @Override
    public CheckRequest getCheckRequestById(Long id) {
        return checkRequestReader.read(id);
    }
}
