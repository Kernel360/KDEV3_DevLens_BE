package com.seveneleven.controller;

import com.seveneleven.dto.*;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.service.AdminCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/companies")
public class CompanyController implements CompanyDocs{
    private final AdminCompanyService adminCompanyService;

    /*
        함수명 : createCompany
        목적 : 회사 생성하여 db에 저장
     */
    @PostMapping("")
    public ResponseEntity<APIResponse<PostCompany.Response>> createCompany(@RequestBody PostCompany.Request companyRequest) {
        return ResponseEntity
                .status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED, adminCompanyService.createCompany(companyRequest)));
    }

    /*
        함수명 : readCompany
        목적 : 회사 상세 정보 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<GetCompanyDetail.Response>> readCompany(
            @PathVariable Long id,
            @RequestParam(value = "page") Integer page
    ) {
        var company = adminCompanyService.getCompanyResponse(id, page);
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, company));
    }

    /*
        함수명 : readCompanyList
        목적 : 회사 목록 조회
     */
    @GetMapping("")
    public ResponseEntity<APIResponse<PaginatedResponse<GetCompanies.Response>>> readCompanyList(@RequestParam(value = "page") Integer page) {
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, adminCompanyService.getListOfCompanies(page)));
    }

    /*
        함수명 : searchCompaniesByName
        함수 목적 : 회사 검색
     */
    @GetMapping("/search")
    public ResponseEntity<APIResponse<PaginatedResponse<GetCompanies.Response>>> searchCompaniesByName(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page") Integer page
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(APIResponse.success(SuccessCode.OK, adminCompanyService.searchCompaniesByName(name,page)));
    }

    /*
        함수명 : updateCompany
        목적 : 회사 상세 정보 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<PutCompany.Response>> updateCompany(
            @PathVariable Long id,
            @RequestBody PutCompany.Request request
    ) {
        return ResponseEntity
                .status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED, adminCompanyService.updateCompany(id, request)));
    }

    /*
        함수명 : deleteCompany
        목적 : 회사 상세 정보 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Object>> deleteCompany(
            @PathVariable Long id
    ) {
        adminCompanyService.deleteCompany(id);
        return ResponseEntity
                .status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }

    /*
        함수명 : readAllCompany
        목적 : 회사 전체 정보 전달
     */
    @GetMapping("/all")
    public ResponseEntity<APIResponse<List<GetAllCompanies>>> readAllCompany() {
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, adminCompanyService.getAllCompanies()));
    }
}
