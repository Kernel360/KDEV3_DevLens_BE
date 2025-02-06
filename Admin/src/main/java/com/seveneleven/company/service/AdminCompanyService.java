package com.seveneleven.company.service;

import com.seveneleven.company.dto.*;
import com.seveneleven.entity.member.Company;
import com.seveneleven.response.PaginatedResponse;

import java.util.List;

public interface AdminCompanyService {
    PostCompany.Response createCompany(PostCompany.Request companyRequest);
    Company getCompany(Long companyId);
    GetCompanyDetail.Response getCompanyDetail(Long id);
    PaginatedResponse<GetCompanies.Response> getListOfCompanies(Integer page);
    PaginatedResponse<GetCompanies.Response> searchCompaniesByName(String name, Integer page);
    PutCompany.Response updateCompany(Long id, PutCompany.Request request);
    void deleteCompany(Long id);
    List<GetAllCompanies> getAllCompanies();
    GetCompanyMember.Response getCompanyMember(Long companyId);
}
