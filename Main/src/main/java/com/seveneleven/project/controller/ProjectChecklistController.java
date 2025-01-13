package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/checklists")
public class ProjectChecklistController implements ProjectChecklistDocs {

    private final ProjectChecklistFacade projectChecklistFacade;

    /**
     * 함수명 : getProjectStepAndChecklist
     * 해당 프로젝트의 모든 단계와 체크리스트 목록을 반환하는 함수
     */
    @GetMapping("/{projectId}")
    public ResponseEntity<APIResponse<GetProjectStep.Response>> getProjectStepAndChecklist(
            @PathVariable Long projectId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.success(SuccessCode.OK, projectChecklistFacade.getProjectStepAndChecklist(projectId)));
    }

    /**
     * 함수명 : getStepChecklist
     * 해당 단계의 체크리스트 목록을 반환하는 함수
     */
    @GetMapping("/{stepId}")
    public ResponseEntity<APIResponse<GetStepChecklist.Response>> getProjectChecklist(
            @PathVariable Long stepId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.success(SuccessCode.OK, projectChecklistFacade.getStepChecklist(stepId)));
    }

    /**
     * 함수명 : postProjectChecklist
     * 해당 프로젝트 단계에 체크리스트를 추가하는 함수
     */
    @PostMapping("")
    public ResponseEntity<APIResponse<PostProjectChecklist.Response>> postProjectChecklist(
            @RequestBody PostProjectChecklist.Request request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.success(SuccessCode.CREATED, projectChecklistFacade.postProjectChecklist(request)));
    }

    /**
     * 함수명 : putProjectChecklist
     * 해당 체크리스트를 수정하는 함수
     */
    @PutMapping("")
    public ResponseEntity<APIResponse<PutProjectChecklist.Response>> putProjectChecklist(
            @RequestBody PutProjectChecklist.Request request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(SuccessCode.OK, projectChecklistFacade.putProjectChecklist(request)));
    }

    /**
     * 함수명 : deleteProjectChecklist
     * 해당 체크리스트를 삭제하는 함수
     */
    @DeleteMapping("/{checklistId}")
    public ResponseEntity<APIResponse<DeleteProjectChecklist.Response>> deleteProjectChecklist(
            @PathVariable Long checklistId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(SuccessCode.DELETED, projectChecklistFacade.deleteProjectChecklist(checklistId)));
    }

    @GetMapping("/applications/{applicationId}")
    public ResponseEntity<APIResponse<GetProjectChecklistApplication.Response>> getProjectChecklistApplication(
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                        .body(APIResponse.success(SuccessCode.OK, projectChecklistFacade.getProjectChecklistApplication(applicationId)));
    }

    /**
     * 함수명 : postProjectChecklistApplication
     * 해당 체크리스트에 체크 승인 요청을 보내는 함수 v
     */
    @PostMapping(value = "/applications", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<APIResponse<PostProjectChecklistApplication.Response>> postProjectChecklistApplication(
            @RequestBody PostProjectChecklistApplication.Request requestDto,
            @RequestBody List<MultipartFile> files,
            HttpServletRequest request
    ) {
        // TODO - 파일 관련 처리 필요.

        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(SuccessCode.CREATED, projectChecklistFacade.postProjectChecklistApplication(requestDto, request)));
    }

    /**
     * 함수명 : postProjectChecklistAccept
     * 해당 체크리스트 승인 요청을 승인 처리하는 함수
     */
    @PostMapping("/accept/{applicationId}")
    public ResponseEntity<APIResponse<PostProjectChecklistAccept.Response>> postProjectChecklistAccept(
            @PathVariable Long applicationId,
            HttpServletRequest request
    ) {
        // TODO - memberId 추가 필요
        Long memberId = 1L;

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.success(
                        SuccessCode.CREATED,
                        projectChecklistFacade.postProjectChecklistAccept(applicationId, memberId, request))
                );
    }

    /**
     * 함수명 : postProjectChecklistReject
     * 해당 체크리스트 승인 요청을 반려 처리하는 함수
     */
    @PostMapping("/reject/{applicationId}")
    public ResponseEntity<APIResponse<PostProjectChecklistReject.Response>> postProjectChecklistReject(
            @RequestPart PostProjectChecklistReject.Request requestDto,
            @RequestPart List<File> files,
            HttpServletRequest request
    ) {
        // TODO - CustomUserDetails - UserId 찾기 코드 필요
        Long memberId = 1L;
        PostProjectChecklistReject.Response response = projectChecklistFacade.postProjectChecklistReject(
                requestDto,
                memberId,
                request
        );

        // TODO - 파일 등록 필요.

        return ResponseEntity.status(SuccessCode.CREATED.getStatusCode())
                .body(APIResponse.success(SuccessCode.CREATED, response));
    }
}