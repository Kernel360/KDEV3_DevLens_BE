package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.methodauthorize.annotation.ApproverAuthorize;
import com.seveneleven.util.methodauthorize.annotation.ParticipantAuthorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectStepController implements ProjectStepDocs {

    private final ProjectStepFacade projectStepFacade;

    /**
     * 함수명 : getProjectStepAndChecklist
     * 해당 프로젝트의 모든 단계와 체크리스트 목록을 반환하는 함수
     */
    @ParticipantAuthorize
    @GetMapping("/{projectId}/steps")
    public ResponseEntity<APIResponse<GetProjectStep.Response>> getProjectStepAndChecklist(
            @PathVariable Long projectId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.success(SuccessCode.OK, projectStepFacade.getProjectStepAndChecklist(projectId)));
    }

    /**
     * 함수명 : postProjectStep
     * 해당 프로젝트에 프로젝트 단계를 추가하는 함수
     */
    @ApproverAuthorize
    @PostMapping("/{projectId}/steps")
    public ResponseEntity<APIResponse<PostProjectStep.Response>> postProjectStep(
            @PathVariable Long projectId,
            @RequestBody PostProjectStep.Request requestDto
    ) {
        return ResponseEntity.status(SuccessCode.CREATED.getStatusCode())
                .body(APIResponse.success(SuccessCode.CREATED, projectStepFacade.postProjectStep(projectId, requestDto)));
    }

    /**
     * 함수명 : putProjectStep
     * 프로젝트 단계를 수정하는 함수
     */
    @ApproverAuthorize
    @PutMapping("/{projectId}/steps/{stepId}")
    public ResponseEntity<APIResponse<PutProjectStep.Response>> putProjectStep(
            @PathVariable Long projectId,
            @PathVariable Long stepId,
            @RequestBody PutProjectStep.Request requestDto
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, projectStepFacade.putProjectStep(stepId, requestDto)));
    }

    /**
     * 함수명 : deleteProjectStep
     * 프로젝트 단계를 삭제하는 함수
     */
    @ApproverAuthorize
    @DeleteMapping("/{projectId}/steps/{stepId}")
    public ResponseEntity<APIResponse<DeleteProjectStep.Response>> deleteProjectStep(
        @PathVariable Long projectId,
        @PathVariable Long stepId
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, projectStepFacade.deleteProjectStep(projectId, stepId)));
    }
}
