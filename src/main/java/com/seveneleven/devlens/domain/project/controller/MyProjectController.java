package com.seveneleven.devlens.domain.project.controller;

import com.seveneleven.devlens.domain.project.dto.getProjectList;
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
    public APIResponse<getProjectList.Response> getMyProject() {
        // Create test data for GetMyProjectResponseDto
        List<getProjectList.GetMyProjectResponseDto> myProjects = List.of(
                new getProjectList.GetMyProjectResponseDto(1L, "Project A", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 6, 30), 101L, "Customer A"),
                new getProjectList.GetMyProjectResponseDto(2L, "Project B", LocalDate.of(2023, 2, 1), LocalDate.of(2023, 7, 31), 102L, "Customer B")
        );

        // Create test data for GetCompanyProjectResponseDto
        List<getProjectList.GetCompanyProjectResponseDto> companyProjects = List.of(
                new getProjectList.GetCompanyProjectResponseDto(3L, "Company Project A", LocalDate.of(2023, 3, 1), LocalDate.of(2023, 8, 30), 201L, "Company A"),
                new getProjectList.GetCompanyProjectResponseDto(4L, "Company Project B", LocalDate.of(2023, 4, 1), LocalDate.of(2023, 9, 30), 202L, "Company B")
        );

        // Create the Response object and set the data
        getProjectList.Response response = new getProjectList.Response(myProjects, companyProjects);

        // Return the APIResponse
        return APIResponse.success(response);
    }
}
