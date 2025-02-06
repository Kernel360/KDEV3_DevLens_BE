package com.seveneleven.company.repository;

import com.seveneleven.company.dto.GetAllCompanies;
import com.seveneleven.company.dto.GetCompanies;
import com.seveneleven.company.dto.GetCompanyMember;
import com.seveneleven.company.exception.CompanyNotFoundException;
import com.seveneleven.company.service.AdminCompanyReader;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.YN;
import com.seveneleven.project.dto.GetProject;
import com.seveneleven.project.repository.AdminProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public List<GetAllCompanies> getAllCompanies() {
        return companyRepository.findAllByIsActiveOrderByCompanyNameAsc(YN.Y).stream()
                .map(GetAllCompanies::of).toList();
    }
}
