package com.seveneleven.project.service.authorization;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.repository.ProjectAuthorizationRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizationReaderImpl implements AuthorizationReader {

    private final ProjectAuthorizationRepository projectAuthorizationRepository;

    @Override
    public List<ProjectAuthorization> readByProjectId(Long projectId) {
        return projectAuthorizationRepository.findByProjectIdAndIsActiveOrderById(projectId, YesNo.YES);
    }

    @Override
    public List<ProjectAuthorization> readByUserId(Long memberId) {
        return projectAuthorizationRepository.findByMemberIdAndIsActiveOrderById(memberId, YesNo.YES);
    }

    @Override
    public ProjectAuthorization readByStepIdAndUserId(Long projectId, Long userId) {
        return projectAuthorizationRepository.findByProjectIdAndMemberIdAndIsActive(projectId, userId, YesNo.YES)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
