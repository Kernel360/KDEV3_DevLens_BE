package com.seveneleven.devlens.domain.admin.db;

import com.seveneleven.devlens.domain.admin.dto.CompanyDto;
import com.seveneleven.devlens.domain.member.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyResponseConverter implements EntityConverter<CompanyDto.CompanyResponse, Company> {
    /*
        함수명 : toDTO
        함수 목적 : response 를 DTO 로 변환
     */
    @Override
    public CompanyDto.CompanyResponse toDTO(Company company) {
        return new CompanyDto.CompanyResponse(
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
        함수 목적 : Dto 를 Entity 로 변환
     */
    @Override
    public Company toEntity(CompanyDto.CompanyResponse companyResponse) {
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
