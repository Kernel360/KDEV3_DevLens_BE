package com.seveneleven.project.service;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.ProjectAuthorization;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.repository.AdminMemberRepository;
import com.seveneleven.project.dto.PatchAuthorization;
import com.seveneleven.project.repository.AdminProjectAuthorizationHistoryRepository;
import com.seveneleven.project.repository.AdminProjectAuthorizationRepository;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdminProjectAuthorizationStoreImpl implements AdminProjectAuthorizationStore {

    private final AdminProjectAuthorizationRepository projectAuthorizationRepository;
    private final AdminMemberRepository memberRepository;
    private final AdminProjectAuthorizationHistoryRepository projectAuthorizationHistoryRepository;

    @Override
    public List<ProjectAuthorization> store(
            List<PatchAuthorization.CustomerMemberAuthorization> customers,
            List<PatchAuthorization.DeveloperMemberAuthorization> developers,
            List<ProjectAuthorization> existingProjectAuthorizations,
            Project project
    ) {
        // DTO에 고객과 개발자 권한이 각각 별도의 리스트로 들어오므로 합쳐서 처리합니다.
        List<PatchAuthorization.MemberAuthorization> requestAuthorizations = new ArrayList<>();
        if (customers != null) {
            requestAuthorizations.addAll(customers);
        }
        if (developers != null) {
            requestAuthorizations.addAll(developers);
        }
        List<ProjectAuthorization> result = new ArrayList<>();
        // 요청에 담긴 각 권한 정보를 처리합니다.
        for (PatchAuthorization.MemberAuthorization requestAuth : requestAuthorizations) {
            try {
                result.add(processAuthorization(requestAuth, existingProjectAuthorizations, project));
            } catch (BusinessException e) {
//                responseDto.getFailList().add(
//                        PostProject.FailList.toDto(
//                                requestAuth,
//                                e.getErrorCode().getStatus(),
//                                e.getMessage()
//                        )
//                );
            }
        }
        // 남은 기존 권한(요청에 포함되지 않은 것)은 삭제 처리합니다.
        delete(existingProjectAuthorizations);
        return result;
    }

    /**
     * 요청으로 들어온 권한 정보(request)를 기반으로,
     * 기존 권한(existingAuthList) 중 일치하는 항목이 있으면 업데이트,
     * 없으면 신규 생성하는 비즈니스 로직을 수행합니다.
     */
    private ProjectAuthorization processAuthorization(
            PatchAuthorization.MemberAuthorization requestDto,
            List<ProjectAuthorization> existingAuthList,
            Project project
    ) {
        if(requestDto instanceof PatchAuthorization.BaseMemberAuthorization request) {
            Member member = memberRepository.findByIdAndStatus(request.getMemberId(), MemberStatus.ACTIVE)
                    .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

            ProjectAuthorization matchedAuthorization = null;
            for (ProjectAuthorization pa : existingAuthList) {
                if (Objects.equals(request.getMemberId(), pa.getMember().getId())) {
                    matchedAuthorization = pa;
                    break;
                }
            }

            if (matchedAuthorization != null) {
                // 해당 권한은 요청에 포함되었으므로 기존 리스트에서 제거하여 나중에 남은 항목은 삭제 처리 대상으로 남깁니다.
                existingAuthList.remove(matchedAuthorization);
                // 기존 권한의 인증코드와 요청의 인증코드가 동일하면 업데이트할 필요가 없습니다.
                if (request.getProjectAuthorization().equals(matchedAuthorization.getAuthorizationCode())) {
                    return matchedAuthorization;
                }
                // 변경사항이 있을 경우, 수정합니다.
                // 기존 코드에서는 getMemberDivision()를 사용했으나 이제는 getMemberType()로 대체합니다.
                matchedAuthorization.edit(request.getMemberType(), request.getProjectAuthorization());
                return projectAuthorizationRepository.save(matchedAuthorization);
            } else {
                // 기존에 해당 멤버에 대한 권한 정보가 없으면 신규 생성합니다.
                ProjectAuthorization newAuthorization = request.toEntity(project, member);
                return projectAuthorizationRepository.save(newAuthorization);
            }
        }
        return null;
    }

    /**
     * 요청에 포함되지 않은 기존 권한들에 대해 삭제(히스토리 저장) 처리합니다.
     */
    private void delete(List<ProjectAuthorization> remainingAuthorizations) {
        for (ProjectAuthorization authorization : remainingAuthorizations) {
            projectAuthorizationHistoryRepository.save(authorization.delete());
        }
    }
}
