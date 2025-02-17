package com.seveneleven.util.methodauthorize;

public interface ProjectAuthorizationCheckService {
    boolean checkParticipant(Long memberId, Long projectId);
    boolean checkApprover(Long memberId, Long projectId);
    boolean checkCustomerParticipant(Long memberId, Long projectId);
    boolean checkCustomerApprover(Long memberId, Long projectId);
    boolean checkDeveloperParticipant(Long memberId, Long projectId);
    boolean checkDeveloperApprover(Long memberId, Long projectId);
    boolean checkEqualsWriter(Long memberId, Long postId);

}
