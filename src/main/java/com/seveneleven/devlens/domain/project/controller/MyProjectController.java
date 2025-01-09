package com.seveneleven.devlens.domain.project.controller;

import com.seveneleven.devlens.domain.project.dto.GetProjectList;
import com.seveneleven.devlens.domain.project.service.ProjectService;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class MyProjectController implements MyProjectDocs {

    private final ProjectService projectService;

    /**
     * 함수명 : getMyProject()
     * 현재 진행중인 내 프로젝트와 우리 회사의 프로젝트를 반환하는 함수
     */
    @GetMapping("/list")
    public APIResponse<GetProjectList.Response> getMyProject() {
        return APIResponse.success(SuccessCode.OK, projectService.getProjectList());
    }
}
