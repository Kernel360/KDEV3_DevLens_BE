package com.seveneleven.util.methodauthorize;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.project.repository.ProjectAuthorizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("projectAuthorizationCheckService")
@Slf4j
@RequiredArgsConstructor
public class ProjectAuthorizationCheckServiceImpl implements ProjectAuthorizationCheckService {

    private final ProjectAuthorizationRepository projectAuthorizationRepository;

    @Override
    public boolean checkParticipant(Long memberId, Long projectId) {
        Optional<ProjectAuthorization> authorization =
                projectAuthorizationRepository
                        .findByProjectIdAndMemberIdAndIsActive(projectId, memberId, YesNo.YES);
        if(authorization.isPresent()) {
            return true;
        }
        log.error("memberId : {} not have authorization in project : {}", memberId, projectId);
        return false;
    }

    @Override
    public boolean checkApprover(Long memberId, Long projectId) {
        Optional<ProjectAuthorization> authorization =
                projectAuthorizationRepository
                        .findByProjectIdAndMemberIdAndIsActive(projectId, memberId, YesNo.YES);

        if(authorization.isPresent() && "APPROVER".equals(authorization.get().getAuthorizationCode())) {
            return true;
        }
        log.error("memberId : {} not have authorization in project : {}", memberId, projectId);
        return false;
    }
}
