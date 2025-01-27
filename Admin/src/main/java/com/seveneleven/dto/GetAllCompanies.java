package com.seveneleven.dto;

import com.seveneleven.entity.member.Company;
import lombok.Getter;

@Getter
public class GetAllCompanies {
    private Long id;
    private String companyName;
    private GetAllCompanies(Company company) {
        id = company.getId();
        companyName = company.getCompanyName();
    }

    public static GetAllCompanies of(Company company) {
        return new GetAllCompanies(company);
    }
}
