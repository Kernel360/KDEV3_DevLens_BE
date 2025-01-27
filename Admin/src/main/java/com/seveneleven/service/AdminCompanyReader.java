package com.seveneleven.service;

import com.seveneleven.dto.GetAllCompanies;
import com.seveneleven.dto.GetCompanies;
import com.seveneleven.dto.GetProject;
import com.seveneleven.entity.member.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminCompanyReader {
    Company getCompany(Long id);
    Company getActiveCompany(Long id);
    Page<GetProject.Response> getCompanyProject(Pageable pageable, Long id);
    Page<GetCompanies.Response> getCompanies(Pageable pageable);
    Page<GetCompanies.Response> getCompaniesBySearchTerm(String searchTerm, Pageable pageable);
    List<GetAllCompanies> getAllCompanies();
}
