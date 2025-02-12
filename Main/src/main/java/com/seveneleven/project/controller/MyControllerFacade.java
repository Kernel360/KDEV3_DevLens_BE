package com.seveneleven.project.controller;

import com.seveneleven.company.service.CompanyService;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.project.Project;
import com.seveneleven.member.service.MemberService;
import com.seveneleven.project.dto.GetCompanyProject;
import com.seveneleven.project.dto.GetMyProjectList;
import com.seveneleven.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MyControllerFacade {

    private final ProjectService projectService;
    private final CompanyService companyService;
    private final MemberService memberService;

    /**
     * 함수명 : getMyProjectList
     * 해당 회원이 참여중인 프로젝트를 반환하는 함수
     */
    @Transactional(readOnly = true)
    public GetMyProjectList.Response getMyProjectList(Long memberId, String filter) {
        Member member = memberService.getMember(memberId);
        List<Project> findProjects = projectService.getMyProjectList(member.getId(), filter);

        List<GetMyProjectList.GetMyProjectResponseInfo> result = findProjects.stream()
                .map(project ->
                        GetMyProjectList.GetMyProjectResponseInfo.toDto(
                                project,
                                projectService.getProjectTags(project.getId())
                        ))
                .toList();

        return GetMyProjectList.Response.toDto(result);
    }

    @Transactional(readOnly = true)
    public GetCompanyProject.Response getCompanyProject(Long companyId, String filter) {
        Company company = companyService.getCompany(companyId);

        List<Project> findProjects = projectService.getCompanyProject(company.getId(), filter);

        List<GetCompanyProject.CompanyProject> companyProjects = findProjects.stream()
                .map(project ->
                        GetCompanyProject.CompanyProject.toDto(
                                project,
                                projectService.getProjectTags(project.getId())
                        ))
                .toList();

        return GetCompanyProject.Response.toDto(company.getId(), companyProjects);
    }
}
