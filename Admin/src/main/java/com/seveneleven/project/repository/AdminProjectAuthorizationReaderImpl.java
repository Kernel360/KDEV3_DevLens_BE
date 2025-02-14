package com.seveneleven.project.repository;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.project.service.AdminProjectAuthorizationReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdminProjectAuthorizationReaderImpl implements AdminProjectAuthorizationReader {

    private final AdminProjectAuthorizationRepository authorizationRepository;

    @Override
    public List<ProjectAuthorization> getAllByProjectId(Long projectId) {
        return authorizationRepository.findByProjectIdAndIsActive(projectId, YesNo.YES);
    }
}
