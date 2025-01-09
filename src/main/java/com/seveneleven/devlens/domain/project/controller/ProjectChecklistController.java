package com.seveneleven.devlens.domain.project.controller;

import com.seveneleven.devlens.domain.project.dto.*;
import com.seveneleven.devlens.domain.project.service.ProjectStepService;
import com.seveneleven.devlens.global.entity.YesNo;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/checklist")
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
    public APIResponse<PutProjectChecklist.Response> putProjectChecklist(
            @RequestBody PutProjectChecklist.Request request
    ) {
        PutProjectChecklist.Response response = new PutProjectChecklist.Response(
                1L,                                 // id
                "Updated Checklist Name",           // name
                "Updated Checklist Description",    // description
                YesNo.YES                                // isChecked
        );

        return APIResponse.success(response);
    }

    /**
     * 함수명 : deleteProjectChecklist
     * 해당 체크리스트를 삭제하는 함수
     */
    @DeleteMapping("/{checklistId}")
    public APIResponse<DeleteProjectChecklist.Response> deleteProjectChecklist(
            @PathVariable Long checklistId
    ) {
        DeleteProjectChecklist.Response response = new DeleteProjectChecklist.Response(
                checklistId,                        // checklistId
                YesNo.YES                                // checklistStatus
        );

        return APIResponse.success(response);
    }

    /**
     * 함수명 : postProjectChecklistApplication
     * 해당 체크리스트에 체크 승인 요청을 보내는 함수
     */
    @PostMapping("/application")
    public APIResponse<PostProjectChecklistApplication.Response> postProjectChecklistApplication(
            @RequestBody PostProjectChecklistApplication.Request request
    ) {
        PostProjectChecklistApplication.Response response = new PostProjectChecklistApplication.Response(
                1L,                              // id
                100L,                            // projectId
                200L,                            // stepId
                300L,                            // checklistId
                "Test description for the checklist application.", // description
                "requester123",                  // requesterId
                "192.168.0.1",                   // requesterIp
                YesNo.YES,                          // hasFile
                YesNo.NO                            // hasLink
        );

        return APIResponse.success(response);
    }

    /**
     * 함수명 : postProjectChecklistAccept
     * 해당 체크리스트 승인 요청을 승인 처리하는 함수
     */
    @PostMapping("/accept/{applicationId}")
    public APIResponse<PostProjectChecklistAccept.Response> postProjectChecklistAccept(
            @PathVariable Long applicationId
    ) {

        PostProjectChecklistAccept.Response response = new PostProjectChecklistAccept.Response(
                100L,         // projectId
                200L,         // checklistId
                YesNo.YES,         // checklistStatus (mocked as YES)
                85.5          // checkRate (mocked as 85.5%)
        );

        return APIResponse.success(response);
    }

    /**
     * 함수명 : postProjectChecklistReject
     * 해당 체크리스트 승인 요청을 반려 처리하는 함수
     */
    @PostMapping("/reject/{applicationId}")
    public APIResponse<PostProjectChecklistReject.Response> postProjectChecklistReject(
            @PathVariable Long applicationId
    ) {
        PostProjectChecklistReject.Response response = new PostProjectChecklistReject.Response(
                applicationId,                 // applicationId
                YesNo.NO,                          // approvalStatus (mocked as NO)
                123L,                          // processorId (mocked ID)
                "192.168.1.100",               // processorIp
                LocalDateTime.now(),           // processDate (current date and time)
                "Invalid data submitted",      // rejectReason (mocked reason)
                YesNo.YES,                          // hasFile (mocked as YES)
                YesNo.NO                           // hasLink (mocked as NO)
        );

        return APIResponse.success(response);
    }
}