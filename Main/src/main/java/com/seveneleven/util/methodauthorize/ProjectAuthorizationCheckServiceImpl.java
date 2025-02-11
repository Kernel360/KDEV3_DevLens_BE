package com.seveneleven.util.methodauthorize;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.project.repository.ProjectAuthorizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProjectAuthorizationCheckServiceImpl implements ProjectAuthorizationCheckService {

    private final ProjectAuthorizationRepository projectAuthorizationRepository;

    @Override
    public boolean checkParticipant(Long memberId, Long projectId) {
        Optional<ProjectAuthorization> authorization =
                projectAuthorizationRepository
                        .findByProjectIdAndMemberIdAndIsActive(projectId, memberId, YesNo.YES);

        return authorization.isPresent();
    }

    @Override
    public boolean checkApprover(Long memberId, Long projectId) {
        Optional<ProjectAuthorization> authorization =
                projectAuthorizationRepository
                        .findByProjectIdAndMemberIdAndIsActive(projectId, memberId, YesNo.YES);

        return authorization.isPresent() && "APPROVER".equals(authorization.get().getAuthorizationCode());
    }
}
