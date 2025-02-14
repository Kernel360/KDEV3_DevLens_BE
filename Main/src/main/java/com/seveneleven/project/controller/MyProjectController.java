package com.seveneleven.project.controller;

import com.seveneleven.project.dto.GetCompanyProject;
import com.seveneleven.project.dto.GetMyProjectList;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MyProjectController implements MyProjectDocs {

    private final MyProjectControllerFacade myProjectControllerFacade;

    /**
     * 함수명 : getMyProject()
     * 현재 진행중인 내 프로젝트와 우리 회사의 프로젝트를 반환하는 함수
     */
    @GetMapping("/projects")
    public ResponseEntity<APIResponse<GetMyProjectList.Response>> getMyProject(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(value = "filter", required = false) String filter
    ) {
        return ResponseEntity
                .status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(
                        SuccessCode.OK,
                        myProjectControllerFacade.getMyProjectList(
                                userDetails.getMember().getId(),
                                filter
                        )));
    }

    /**
     * 함수명 : getCompanyProject()
     * 자신의 회사의 프로젝트를 반환하는 함수
     */
    @Override
    @GetMapping("/companies/{companyId}/projects")
    public ResponseEntity<APIResponse<GetCompanyProject.Response>> getMyCompanyProject(
            @PathVariable Long companyId,
            @RequestParam(value = "filter", required = false) String filter
    ) {
        return ResponseEntity.status(SuccessCode.OK.getStatusCode())
                .body(APIResponse.success(SuccessCode.OK, myProjectControllerFacade.getCompanyProject(companyId, filter)));
    }
}
