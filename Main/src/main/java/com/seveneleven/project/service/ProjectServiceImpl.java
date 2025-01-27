package com.seveneleven.project.service;

import com.seveneleven.entity.project.Project;
import com.seveneleven.member.service.MemberService;
import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.GetProjectList;
import com.seveneleven.project.service.dashboard.ProjectReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectReader projectReader;
    private final MemberService memberService;

    @Override
    public GetProjectList.Response getProjectList(Long memberId) {
        // TODO - memberService -> Reader로 변경 필요
        return projectReader.getProjectList(memberId, memberService.getCompanyIdById(memberId));
    }

    @Override
    public GetProjectDetail.Response getProjectDetail(Long projectId) {
        return projectReader.getProjectDetail(projectId);
    }

    @Override
    public Project getProject(Long projectId) {
        return projectReader.read(projectId);
    }
}
