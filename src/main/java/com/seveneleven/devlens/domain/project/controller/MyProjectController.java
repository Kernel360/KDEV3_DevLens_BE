package com.seveneleven.devlens.domain.project.controller;

import com.seveneleven.devlens.domain.project.dto.GetProjectList;
import com.seveneleven.devlens.global.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class MyProjectController implements MyProjectDocs {

    /**
     * 함수명 : getMyProject()
     * 현재 진행중인 내 프로젝트와 우리 회사의 프로젝트를 반환하는 함수
     */
    @GetMapping("/list")
    public APIResponse<GetProjectList.Response> getMyProject() {
        List<GetProjectList.GetMyProjectResponseInfo> myProjects = List.of(
                new GetProjectList.GetMyProjectResponseInfo(1L, "Project A", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 6, 30), 101L, "Customer A"),
                new GetProjectList.GetMyProjectResponseInfo(2L, "Project B", LocalDate.of(2023, 2, 1), LocalDate.of(2023, 7, 31), 102L, "Customer B")
        );

        List<GetProjectList.GetCompanyProjectResponseInfo> companyProjects = List.of(
                new GetProjectList.GetCompanyProjectResponseInfo(3L, "Company Project A", LocalDate.of(2023, 3, 1), LocalDate.of(2023, 8, 30), 201L, "Company A"),
                new GetProjectList.GetCompanyProjectResponseInfo(4L, "Company Project B", LocalDate.of(2023, 4, 1), LocalDate.of(2023, 9, 30), 202L, "Company B")
        );

        GetProjectList.Response response = new GetProjectList.Response(myProjects, companyProjects);

        return APIResponse.success(response);
    }
}
