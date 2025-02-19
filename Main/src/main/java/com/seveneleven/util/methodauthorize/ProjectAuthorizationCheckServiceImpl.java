package com.seveneleven.util.methodauthorize;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.project.repository.ProjectAuthorizationRepository;
import com.seveneleven.project.repository.ProjectRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component("projectAuthorizationCheckService")
@Slf4j
@RequiredArgsConstructor
public class ProjectAuthorizationCheckServiceImpl implements ProjectAuthorizationCheckService {

    private final ProjectAuthorizationRepository projectAuthorizationRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    private static final String DEVELOPER = "DEVELOPER";
    private static final String CUSTOMER = "CUSTOMER";

    private static final String APPROVER = "APPROVER";
    private static final String PARTICIPANT = "PARTICIPANT";

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

        if(authorization.isPresent() && Objects.equals(APPROVER, authorization.get().getAuthorization())) {
            return true;
        }
        log.error("memberId : {} not have authorization in project : {}", memberId, projectId);
        return false;
    }

    @Override
    public boolean checkCustomerParticipant(Long memberId, Long projectId) {
        if(checkCompanyAndAuthorization(memberId, projectId, CUSTOMER, PARTICIPANT)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkCustomerApprover(Long memberId, Long projectId) {
        if(checkCompanyAndAuthorization(memberId, projectId, CUSTOMER, APPROVER)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkDeveloperParticipant(Long memberId, Long projectId) {
        if(checkCompanyAndAuthorization(memberId, projectId, DEVELOPER, PARTICIPANT)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkDeveloperApprover(Long memberId, Long projectId) {
        if(checkCompanyAndAuthorization(memberId, projectId, DEVELOPER, APPROVER)) {
            return true;
        }
        return false;
    }

    private boolean checkCompanyAndAuthorization(
            Long memberId, Long projectId, String companyCode, String authorizationCode
    ) {
        Member member = memberRepository.findByIdAndStatus(memberId, MemberStatus.ACTIVE)
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.NOT_FOUND_MEMBER.getMessage()));
        Company company = member.getCompany();

        Project project = projectRepository.findByIdAndProjectStatusCodeNot(projectId, ProjectStatusCode.DELETED)
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.PROJECT_NOT_FOUND.getMessage()));

        Optional<ProjectAuthorization> authorization =
                projectAuthorizationRepository
                        .findByProjectIdAndMemberIdAndIsActive(projectId, memberId, YesNo.YES);

        if(
                authorization.isPresent() &&
                Objects.equals(authorizationCode, authorization.get().getAuthorization()) &&
                company == (companyCode.equals(CUSTOMER) ? project.getCustomer() : project.getDeveloper())
        ) {
            return true;
        }
        log.error("memberId : {} not have authorization in this action", memberId);
        return false;
    }

    @Override
    public boolean checkEqualsWriter(Long memberId, Long postId) {
        return false;
    }
}
