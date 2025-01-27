package com.seveneleven.repository;

import com.seveneleven.dto.GetCompanies;
import com.seveneleven.dto.GetProject;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.YN;
import com.seveneleven.exception.CompanyNotFoundException;
import com.seveneleven.service.AdminCompanyReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminCompanyReaderImpl implements AdminCompanyReader {
    private final CompanyRepository companyRepository;
    private final AdminProjectRepository adminProjectRepository;

    @Override
    public Company getCompany(Long id) {
        return companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }

    @Override
    public Company getActiveCompany(Long id) {
        return companyRepository.findByIdAndIsActive(id, YN.Y).orElseThrow(CompanyNotFoundException::new);
    }

    @Override
    public Page<GetProject.Response> getCompanyProject(Pageable pageable, Long id) {
        Company company = companyRepository.findByIdAndIsActive(id, YN.Y).orElseThrow(CompanyNotFoundException::new);
        return adminProjectRepository.findByCustomerOrDeveloper(pageable, company, company).map(GetProject.Response::of);
    }

    @Override
    public Page<GetCompanies.Response> getCompanies(Pageable pageable) {
        return companyRepository.findByIsActive(pageable,YN.Y).map(GetCompanies.Response::getCompaniesResponse);
    }

    @Override
    public Page<GetCompanies.Response> getCompaniesBySearchTerm(String searchTerm, Pageable pageable) {
        return companyRepository.findByIsActiveAndCompanyNameContainingIgnoreCase(YN.Y, searchTerm, pageable)
                .map(GetCompanies.Response::getCompaniesResponse);
    }
}
