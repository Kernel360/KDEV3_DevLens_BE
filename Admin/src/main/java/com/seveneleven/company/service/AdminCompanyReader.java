package com.seveneleven.company.service;

import com.seveneleven.company.dto.GetAllCompanies;
import com.seveneleven.company.dto.GetCompanies;
import com.seveneleven.entity.member.Company;
import com.seveneleven.project.dto.GetProjectList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminCompanyReader {
    Company getCompany(Long id);
    Company getActiveCompany(Long id);
    Page<GetProjectList.Response> getCompanyProject(Pageable pageable, Long id);
    Page<GetCompanies.Response> getCompanies(Pageable pageable);
    Page<GetCompanies.Response> getCompaniesBySearchTerm(String searchTerm, Pageable pageable);
    List<GetAllCompanies> getAllCompanies();
}
