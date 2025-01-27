package com.seveneleven.application.adminCompany;

import com.seveneleven.dto.GetCompanies;
import com.seveneleven.dto.GetCompanyDetail;
import com.seveneleven.dto.GetProject;
import com.seveneleven.dto.PostCompany;
import com.seveneleven.response.PaginatedResponse;
import com.seveneleven.service.AdminCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public PaginatedResponse<GetProject.Response> getCompanyProject(Long id, Integer page) {
        return adminCompanyService.getCompanyProject(page, id);
    }

    public PaginatedResponse<GetCompanies.Response> getCompanyList(Integer page){
        return adminCompanyService.getListOfCompanies(page);
    }
}
