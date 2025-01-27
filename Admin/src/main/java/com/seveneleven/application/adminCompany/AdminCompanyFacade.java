package com.seveneleven.application.adminCompany;

import com.seveneleven.dto.*;
import com.seveneleven.response.PaginatedResponse;
import com.seveneleven.service.AdminCompanyService;
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
}
