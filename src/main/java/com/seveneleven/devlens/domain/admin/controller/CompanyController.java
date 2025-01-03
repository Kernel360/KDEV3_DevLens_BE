package com.seveneleven.devlens.domain.admin.controller;

import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.admin.dto.PaginatedResponse;
import com.seveneleven.devlens.domain.admin.service.CompanyCreateService;
import com.seveneleven.devlens.domain.admin.service.CompanyReadService;
import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.response.SuccessCode;
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
    @PostMapping("/create")
    public APIResponse<CompanyDto.CompanyResponse> createCompany(@Valid @RequestBody CompanyDto.CompanyRequest companyRequest) {
        var company = companyCreateService.createCompany(companyRequest);
        return APIResponse.success(company);
    }

    /*
        함수명 : readCompany
        목적 : 회사 상세 정보 조회
     */
    @GetMapping("/{companyId}")
    public APIResponse<CompanyDto.CompanyResponse> readCompany(@PathVariable Long companyId) {
        var company = companyReadService.getCompanyResponse(companyId);
        return APIResponse.success(SuccessCode.OK, "회사 조회가 완료되었습니다.",company);
    }

    /*
        함수명 : readAllCompanies
        목적 : 회사 페이지 조회
     */
    @GetMapping("")
    public APIResponse<PaginatedResponse<CompanyDto.CompanyResponse>> readAllCompanies(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<CompanyDto.CompanyResponse> paging = companyReadService.getListOfCompanies(page);
        PaginatedResponse<CompanyDto.CompanyResponse> response = new PaginatedResponse<>(
                paging.getContent(),
                paging.getNumber(),
                paging.getSize(),
                paging.getTotalElements(),
                paging.getTotalPages(),
                paging.isLast()
        );
        return APIResponse.success(response);
    }

}
