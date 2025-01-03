package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.member.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyRequestConverter implements EntityConverter<CompanyDto.CompanyRequest, Company> {
    /*
        함수명 : toDto
        함수 목적 : entity 를 request 로 변환
     */
    @Override
    public CompanyDto.CompanyRequest toDTO(Company company) {
        return new CompanyDto.CompanyRequest(
                company.getId(),
                company.getCompanyName(),
                company.getRepresentativeName(),
                company.getRepresentativeContact(),
                company.getRepresentativeEmail(),
                company.getAddress(),
                company.getBusinessType(),
                company.getBusinessRegistrationNumber(),
                company.getRepresentativeImageExists(),
                company.getIsActive());
    }
    /*
        함수명 : toEntity
        함수 목적 : request 를 entity 로 변환
     */
    @Override
    public Company toEntity(CompanyDto.CompanyRequest companyResponse) {
        return new Company(
                companyResponse.getId(),
                companyResponse.getCompanyName(),
                companyResponse.getRepresentativeName(),
                companyResponse.getRepresentativeContact(),
                companyResponse.getRepresentativeEmail(),
                companyResponse.getAddress(),
                companyResponse.getBusinessType(),
                companyResponse.getBusinessRegistrationNumber(),
                companyResponse.getRepresentativeImageExists(),
                companyResponse.getIsActive());
    }

}
