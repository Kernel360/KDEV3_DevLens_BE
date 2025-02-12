package com.seveneleven.project.controller;

import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.member.service.MemberService;
import com.seveneleven.project.dto.GetMemberAuthorization;
import com.seveneleven.project.dto.GetProjectAuthorization;
import com.seveneleven.project.dto.PostProjectAuthorization;
import com.seveneleven.project.service.ProjectAuthorizationService;
import com.seveneleven.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProjectAuthorizationFacade {

    private final ProjectService projectService;
    private final MemberService memberService;
    private final ProjectAuthorizationService projectAuthorizationService;

    /**
     * 함수명 : postProjectAuthorization
     * 프로젝트 접근 권한을 편힙하는 함수
     */
    @Transactional
    public PostProjectAuthorization.Response postProjectAuthorization(
            PostProjectAuthorization.Request requestDto,
            Long projectId
    ) {
        Project project = projectService.getProject(projectId);
        return projectAuthorizationService.createProjectAuthorization(project, requestDto);
    }

    /**
     * 함수명 : getProjectAuthorization
     * 해당 단계에 접근할 수 있는 인원을 반환하는 함수
     */
    @Transactional(readOnly = true)
    public GetProjectAuthorization.Response getProjectAuthorization(Long projectId) {
        Project project = projectService.getProject(projectId);
        return projectAuthorizationService.getProjectAuthorization(project);
    }

    @Transactional(readOnly = true)
    public GetMemberAuthorization.Response getMemberAuthorization(Long projectId, Long memberId) {
        Project project = projectService.getProject(projectId);
        Member member = memberService.getMember(memberId);

        return projectAuthorizationService.getMemberAuthorization(project, member);
    }
}
