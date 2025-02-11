package com.seveneleven.util.methodauthorize;

public interface ProjectAuthorizationCheckService {
    boolean checkParticipant(Long memberId, Long projectId);
    boolean checkApprover(Long memberId, Long projectId);
}
