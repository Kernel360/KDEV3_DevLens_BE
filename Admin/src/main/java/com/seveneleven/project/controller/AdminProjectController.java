package com.seveneleven.project.controller;

import com.seveneleven.project.application.AdminProjectFacade;
import com.seveneleven.project.application.AdminProjectHistoryFacade;
import com.seveneleven.project.dto.*;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.PaginatedResponse;
import com.seveneleven.response.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/projects")
public class AdminProjectController implements AdminProjectDocs{
    private final AdminProjectFacade adminProjectFacade;
    private final AdminProjectHistoryFacade adminProjectHistoryFacade;

    /*
        함수명 : newProject
        함수 목적 : 프로젝트 생성
     */
    @PostMapping("")
    public ResponseEntity<APIResponse<PostProject.Response>> newProject(@RequestBody @Valid PostProject.Request request) {
        PostProject.Response project = adminProjectFacade.registerProject(request);
        return ResponseEntity
                .status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED, project));
    }

    /*
        함수명 : checkProjectName
        함수 목적 : 프로젝트 이름 중복 확인 api
     */
    @GetMapping("/check")
    public ResponseEntity<APIResponse<String>> checkProjectName(@RequestParam String name){
        String message = adminProjectFacade.checkProjectNameExists(name);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, message));
    }

    /*
        함수명 : readProject
        함수 목적 : 프로젝트 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<GetProject.Response>> readProject(@PathVariable Long id) {
        GetProject.Response response = adminProjectFacade.getProject(id);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
    }

    /*
        함수명 : getListOfProjects
        함수 목적 : 프로젝트 목록 조회
     */
    @GetMapping("")
    public ResponseEntity<APIResponse<PaginatedResponse<GetProjectList.Response>>> getListOfProjects(@RequestParam(value = "page") Integer page) {
        PaginatedResponse<GetProjectList.Response> response = adminProjectFacade.getListOfProject(page);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
    }

    /*
        함수명 : getListOfProjectHistory
        함수 목적 : 프로젝트 이력 목록 조회
     */
    @GetMapping("/histories")
    public ResponseEntity<APIResponse<PaginatedResponse<GetProjectHistory.Response>>> getListOfProjectHistory(@RequestParam(value = "page") Integer page) {
        PaginatedResponse<GetProjectHistory.Response> response = adminProjectHistoryFacade.getListOfProjectHistory(page);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
    }

    /*
        함수명 : getProjectHistory
        함수 목적 : 프로젝트 이력 조회
     */
    @GetMapping("/{id}/histories")
    public ResponseEntity<APIResponse<GetProjectHistory.Response>> getProjectHistory(@PathVariable Long id) {
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, adminProjectHistoryFacade.getProjectHistory(id)));
    }

    /*
        함수명 : updateProject
        함수 목적 : 프로젝트 이력 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<PutProject.Response>> updateProject(
            @PathVariable Long id,
            @RequestBody @Valid PutProject.Request request
    ){
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, adminProjectFacade.updateProject(id, request)));
    }

    /*
        함수명 : searchHistories
        함수 목적 : 특정 프로젝트 이력 목록 검색
     */
    @GetMapping("/histories/search")
    public ResponseEntity<APIResponse<PaginatedResponse<GetProjectHistory.Response>>> searchHistories(
            @RequestParam String searchTerm,
            @RequestParam Integer page
    ){
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, adminProjectHistoryFacade.searchHistoryByProjectName(searchTerm, page)));
    }
    /*
        함수명 : deleteProject
        함수 목적 : 프로젝트 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteProject(@PathVariable Long id){
        adminProjectFacade.deleteProject(id);
        return ResponseEntity
                .status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }
}
