package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectChecklistController implements ProjectChecklistDocs {

    private final ProjectChecklistFacade projectChecklistFacade;

    /**
     * 함수명 : getStepChecklist
     * 해당 단계의 체크리스트 목록을 반환하는 함수
     */
    @GetMapping("/steps/{stepId}/checklists")
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
    @PostMapping("/steps/{stepId}/checklists")
    public ResponseEntity<APIResponse<PostProjectChecklist.Response>> postProjectChecklist(
            @PathVariable Long stepId,
            @RequestBody PostProjectChecklist.Request request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.success(SuccessCode.CREATED, projectChecklistFacade.postProjectChecklist(stepId, request)));
    }

    /**
     * 함수명 : putProjectChecklist
     * 해당 체크리스트를 수정하는 함수
     */
    @PutMapping("/checklists/{checklistId}")
    public ResponseEntity<APIResponse<PutProjectChecklist.Response>> putProjectChecklist(
            @PathVariable Long checklistId,
            @RequestBody PutProjectChecklist.Request request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(SuccessCode.OK, projectChecklistFacade.putProjectChecklist(checklistId, request)));
    }

    /**
     * 함수명 : deleteProjectChecklist
     * 해당 체크리스트를 삭제하는 함수
     */
    @DeleteMapping("/checklists/{checklistId}")
    public ResponseEntity<APIResponse<DeleteProjectChecklist.Response>> deleteProjectChecklist(
            @PathVariable Long checklistId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(SuccessCode.DELETED, projectChecklistFacade.deleteProjectChecklist(checklistId)));
    }

    /**
     * 함수명 : postProjectChecklistApplication
     * 해당 체크리스트에 체크 승인 요청을 보내는 함수
     */
    @PostMapping(value = "/checklists/{checklistId}/applications", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<APIResponse<PostProjectChecklistApplication.Response>> postProjectChecklistApplication(
            @PathVariable Long checklistId,
            @RequestBody PostProjectChecklistApplication.Request requestDto,
            @RequestBody List<MultipartFile> files,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(
                        SuccessCode.CREATED,
                        projectChecklistFacade.postProjectChecklistApplication(checklistId, requestDto, request))
                );
    }

    /**
     * 함수명 : getProjectChecklistApplication
     * 프로젝트 체크리스트에 승인 요청을 확인하는 함수
     */
    @GetMapping("/checklists/applications/{applicationId}")
    public ResponseEntity<APIResponse<GetProjectChecklistApplication.Response>> getProjectChecklistApplication(
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                        .body(APIResponse.success(
                                SuccessCode.OK,
                                projectChecklistFacade.getProjectChecklistApplication(applicationId)
                        ));
    }

    /**
     * 함수명 : postProjectChecklistAccept
     * 해당 체크리스트 승인 요청을 승인 처리하는 함수
     */
    @PostMapping("/accept/{applicationId}")
    public ResponseEntity<APIResponse<PostProjectChecklistAccept.Response>> postProjectChecklistAccept(
            @PathVariable Long applicationId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.success(
                        SuccessCode.CREATED,
                        projectChecklistFacade.postProjectChecklistAccept(
                                applicationId,
                                customUserDetails.getMember().getId(),
                                request)
                        ));
    }

    /**
     * 함수명 : postProjectChecklistReject
     * 해당 체크리스트 승인 요청을 반려 처리하는 함수
     */
    @PostMapping(name = "/reject/{applicationId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<APIResponse<PostProjectChecklistReject.Response>> postProjectChecklistReject(
            @PathVariable Long applicationId,
            @RequestPart PostProjectChecklistReject.Request requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request
    ) {
        PostProjectChecklistReject.Response response =
                projectChecklistFacade.postProjectChecklistReject(
                        applicationId,
                        requestDto,
                        userDetails.getMember().getId(),
                        request
                );

        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, response));
    }
}