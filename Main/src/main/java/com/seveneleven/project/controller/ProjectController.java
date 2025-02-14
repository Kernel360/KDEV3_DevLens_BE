package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.PatchProjectCurrentStep;
import com.seveneleven.project.service.ProjectService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController implements ProjectDocs {

    private final ProjectService projectService;

    /**
     * 함수명 : getProjectDetail
     * 프로젝트 상세 페이지를 반환하는 함수
     */
    @GetMapping("/{projectId}/detail")
    public ResponseEntity<APIResponse<GetProjectDetail.Response>> getProjectDetail(
            @PathVariable Long projectId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.success(SuccessCode.OK, projectService.getProjectDetail(projectId)));
    }

    /**
     * 함수명 : patchProjectCurrentStep()
     * 프로젝트의 현재 단계를 수정하는 함수
     */
    @Override
    @PatchMapping("/projects/{projectId}/current-steps/{stepId}")
    public ResponseEntity<APIResponse<PatchProjectCurrentStep.Response>> patchProjectCurrentStep(
            @PathVariable Long projectId,
            @PathVariable Long stepId
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(
                        SuccessCode.OK,
                        projectService.patchProjectCurrentStep(projectId, stepId))
                );
    }
}