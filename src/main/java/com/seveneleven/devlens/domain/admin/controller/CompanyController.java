package com.seveneleven.devlens.domain.admin.controller;

import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.admin.dto.PaginatedResponse;
import com.seveneleven.devlens.domain.admin.service.CompanyCreateService;
import com.seveneleven.devlens.domain.admin.service.CompanyReadService;
import com.seveneleven.devlens.global.response.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyCreateService companyCreateService;
    private final CompanyReadService companyReadService;
    /*
        함수명 : createCompany
        목적 : 회사 생성하여 db에 저장
     */
    @PostMapping("/new")
    public APIResponse<CompanyDto.CompanyResponse> createCompany(@Valid @RequestBody CompanyDto.CompanyRequest companyRequest) {
        var company = companyCreateService.createCompany(companyRequest);
        return APIResponse.success(company);
    }

    /*
        함수명 : readCompany
        목적 : 회사 상세 정보 조회
     */
    @GetMapping("/list/{companyId}")
    public APIResponse<CompanyDto.CompanyResponse> readCompany(@PathVariable Long companyId) {
        var company = companyReadService.getCompanyResponse(companyId);
        return APIResponse.success(company);
    }

    /*
        함수명 : readAllCompanies
        목적 : 회사 페이지 조회
     */
    @GetMapping("/list")
    public APIResponse<PaginatedResponse<CompanyDto.CompanyResponse>> readCompanyPage(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<CompanyDto.CompanyResponse> paging = companyReadService.getListOfCompanies(page);
        PaginatedResponse<CompanyDto.CompanyResponse> response = PaginatedResponse.createPaginatedResponse(paging);
        return APIResponse.success(response);
    }
    /*
        함수명 : readCompanyPageTotalPages
        목적 : 회사 전체 페이지 수 조회
     */
    @GetMapping("/total-pages")
    public APIResponse<Integer> readCompanyTotalPages() {
        int pageSize = companyReadService.getTotalPages();
        return APIResponse.success(pageSize);
    }
}
