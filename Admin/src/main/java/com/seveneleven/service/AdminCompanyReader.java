package com.seveneleven.service;

import com.seveneleven.dto.GetCompanies;
import com.seveneleven.dto.GetProject;
import com.seveneleven.entity.member.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminCompanyReader {
    Company getCompany(Long id);
    Company getActiveCompany(Long id);
    Page<GetProject.Response> getCompanyProject(Pageable pageable, Long id);
    Page<GetCompanies.Response> getCompanies(Pageable pageable);
}
