package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.service.ProjectService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController implements ProjectDocs {

    private final ProjectService projectService;

    /**
     * 함수명 : getProjectDetail
     * 프로젝트 상세 페이지를 반환하는 함수
     */
    @GetMapping("/detail/{projectId}")
    public ResponseEntity<APIResponse<GetProjectDetail.Response>> getProjectDetail(
            @PathVariable Long projectId
    ) {
        // TODO - 정렬 조건 추가 필요
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.success(SuccessCode.OK, projectService.getProjectDetail(projectId)));
    }

    // TODO - 검색 api 추가 필요
}