package com.seveneleven.project.controller;

import com.seveneleven.project.dto.*;
import com.seveneleven.project.service.ProjectChecklistService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.file.dto.LinkResponse;
import com.seveneleven.util.security.dto.CustomUserDetails;
import com.seveneleven.util.file.dto.FileMetadataDto;
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
    private final ProjectChecklistService projectChecklistService;

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
    @PostMapping("/checklists/{checklistId}/applications")
    public ResponseEntity<APIResponse<PostProjectChecklistApplication.Response>> postProjectChecklistApplication(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long checklistId,
            @RequestBody PostProjectChecklistApplication.Request requestDto,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(APIResponse.success(
                        SuccessCode.CREATED,
                        projectChecklistFacade.postProjectChecklistApplication(checklistId, user.getMember().getId(), requestDto, request))
                );
    }

    /**
     * 함수명 : postProjectChecklistApplicationFiles
     * 해당 체크리스트에 체크 승인 요청 파일을 업로드하는 함수
     */
    @PostMapping(value = "/checklists/{checklistId}/applications/{applicationId}/files", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<APIResponse<SuccessCode>> postProjectChecklistApplication(
            @PathVariable Long checklistId,
            @PathVariable Long applicationId,
            @RequestParam("files") List<MultipartFile> files,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){

        Long uploaderId = userDetails.getId();

        projectChecklistService.postProjectChecklistApplicationFiles(checklistId, applicationId, uploaderId, files);

        return ResponseEntity.status(SuccessCode.CREATED.getStatusCode())
                .body(APIResponse.success(SuccessCode.CREATED));
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
     * 함수명 : getProjectChecklistApplicationFiles
     * 프로젝트 체크리스트 승인 요청의 파일을 확인하는 함수
     */
    @GetMapping("/checklists/applications/{applicationId}/files")
    public ResponseEntity<APIResponse<List<FileMetadataDto>>> getProjectChecklistApplicationFiles(
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(
                        SuccessCode.OK,
                        projectChecklistService.getApplicationFiles(applicationId)
                ));
    }

    /**
     * 함수명 : getProjectChecklistApplicationLinks
     * 프로젝트 체크리스트 승인 요청의 링크들을 확인하는 함수
     */
    @GetMapping("/checklists/applications/{applicationId}/links")
    public ResponseEntity<APIResponse<List<LinkResponse>>> getProjectChecklistApplicationLinks(
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(
                        SuccessCode.OK,
                        projectChecklistService.getApplicationLinks(applicationId)
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
    @PostMapping("/applications/{applicationId}/reject")
    public ResponseEntity<APIResponse<PostProjectChecklistReject.Response>> postProjectChecklistReject(
            @PathVariable Long applicationId,
            @RequestBody PostProjectChecklistReject.Request requestDto,
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

    @GetMapping("/application/{applicationId}/result")
    public ResponseEntity<APIResponse<GetApplicationResult.Response>> getProjectApplicationResult(
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, projectChecklistFacade.getApplicationResult(applicationId)));
    }

    /**
     * 함수명 : postProjectChecklistRejectFiles
     * 체크리스트 승인 요청 반려 사유에 파일을 등록하는 함수
     */
    @PostMapping("/reject/{applicationId}/files")
    public ResponseEntity<APIResponse<SuccessCode>> postProjectChecklistRejectFile(
            @PathVariable Long applicationId,
            @RequestParam("files") List<MultipartFile> files,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        Long uploaderId = userDetails.getId();

        projectChecklistService.postCheckRejectFiles(applicationId, uploaderId, files);

        return ResponseEntity.status(SuccessCode.CREATED.getStatusCode())
                .body(APIResponse.success(SuccessCode.CREATED));
    }

    /**
     * 함수명 : getProjectChecklistRejectFiles
     * 체크리스트 승인 요청 반려 사유의 파일 목록을 조회하는 함수
     */
    @GetMapping("/reject/{applicationId}/files")
    public ResponseEntity<APIResponse<List<FileMetadataDto>>> getProjectChecklistRejectFiles(
            @PathVariable Long applicationId){

        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(
                        SuccessCode.OK,
                        projectChecklistService.getChecklistRejectFiles(applicationId)
                ));
    }

    /**
     * 함수명 : getProjectChecklistRejectFiles
     * 체크리스트 승인 요청 반려 사유의 링크 목록을 조회하는 함수
     */
    @GetMapping("/reject/{applicationId}/links")
    public ResponseEntity<APIResponse<List<LinkResponse>>> getProjectChecklistRejectLinks(
            @PathVariable Long applicationId
    ){
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(
                        SuccessCode.OK,
                        projectChecklistService.getChecklistRejectLinks(applicationId)
                ));
    }

}