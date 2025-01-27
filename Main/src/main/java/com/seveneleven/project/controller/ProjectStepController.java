package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectStepController implements ProjectStepDocs{

    private final ProjectStepFacade projectStepFacade;

    /**
     * 함수명 : getProjectStepAndChecklist
     * 해당 프로젝트의 모든 단계와 체크리스트 목록을 반환하는 함수
     */
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
    @PostMapping("/steps")
    public ResponseEntity<APIResponse<PostProjectStep.Response>> postProjectStep(
            @RequestBody PostProjectStep.Request requestDto
    ) {
        return ResponseEntity.status(SuccessCode.CREATED.getStatusCode())
                .body(APIResponse.success(SuccessCode.CREATED, projectStepFacade.postProjectStep(requestDto)));
    }

    @PutMapping("/steps")
    public ResponseEntity<APIResponse<PutProjectStep.Response>> putProjectStep(
            @RequestBody PutProjectStep.Request requestDto
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, projectStepFacade.putProjectStep(requestDto)));
    }

    @DeleteMapping("/steps")
    public ResponseEntity<APIResponse<DeleteProjectStep.Response>> deleteProjectStep(
            @RequestBody DeleteProjectStep.Request requestDto
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, projectStepFacade.deleteProjectStep(requestDto)));
    }

    @PostMapping("/{stepId}/authorizations")
    public ResponseEntity<APIResponse<PostProjectAuthorization.Response>> postProjectAuthorization(
            @PathVariable Long stepId,
            @RequestBody PostProjectAuthorization.Request requestDto
    ) {
        PostProjectAuthorization.Response responseDto = projectStepFacade.postProjectAuthorization(requestDto, stepId);

        if(responseDto.getFailList().isEmpty()) {
            return ResponseEntity.status(SuccessCode.CREATED.getStatusCode())
                    .body(APIResponse.success(SuccessCode.OK, responseDto));
        }
        return ResponseEntity.status(SuccessCode.MULTISTATUS.getStatusCode())
                .body(APIResponse.success(SuccessCode.MULTISTATUS, responseDto));
    }

    @GetMapping("/{stepId}/authorizations")
    public ResponseEntity<APIResponse<GetProjectAuthorization.Response>> getProjectAuthorization(
            @PathVariable Long stepId
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, projectStepFacade.getProjectAuthorization(stepId)));
    }
}
