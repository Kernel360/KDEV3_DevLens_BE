package com.seveneleven.company.application;

import com.seveneleven.company.dto.*;
import com.seveneleven.company.service.AdminCompanyService;
import com.seveneleven.entity.member.Company;
import com.seveneleven.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCompanyFacade {
    private final AdminCompanyService adminCompanyService;

    public PostCompany.Response registerCompany(PostCompany.Request request) {
        return adminCompanyService.createCompany(request);
    }

    public GetCompanyDetail.Response getCompanyDetail(Long id) {
        return adminCompanyService.getCompanyDetail(id);
    }

    public PaginatedResponse<GetCompanies.Response> getCompanyList(Integer page) {
        return adminCompanyService.getListOfCompanies(page);
    }

    public PaginatedResponse<GetCompanies.Response> getCompanyBySearchTerm(String term, Integer page) {
        return adminCompanyService.searchCompaniesByName(term, page);
    }

    public PutCompany.Response updateCompany(Long id, PutCompany.Request request) {
        return adminCompanyService.updateCompany(id, request);
    }

    public void deleteCompany(Long id) {
        adminCompanyService.deleteCompany(id);
    }

    public List<GetAllCompanies> getAllCompanies() {
        return adminCompanyService.getAllCompanies();
    }

    public GetCompanyMember.Response getCompanyMember(Long companyId) {
        Company company = adminCompanyService.getCompany(companyId);
        return adminCompanyService.getCompanyMember(company.getId());
    }
}
