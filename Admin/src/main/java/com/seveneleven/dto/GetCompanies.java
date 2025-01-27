package com.seveneleven.dto;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.BusinessType;
import com.seveneleven.entity.member.constant.YN;
import lombok.Getter;

import java.time.LocalDateTime;

public class GetCompanies {
    @Getter
    public static class Response {
        private Long id;
        private String companyName;
        private String representativeName;
        private String representativeContact;
        private String representativeEmail;
        private String address;
        private BusinessType businessType;
        private String businessRegistrationNumber;
        private YN isActive;
        private LocalDateTime createdAt;

        private Response(
                Company company
        ) {
            id = company.getId();
            companyName = company.getCompanyName();
            representativeName = company.getRepresentativeName();
            representativeContact = company.getRepresentativeContact();
            representativeEmail = company.getRepresentativeEmail();
            address = company.getAddress();
            businessType = company.getBusinessType();
            businessRegistrationNumber = company.getBusinessRegistrationNumber();
            isActive = company.getIsActive();
            createdAt = company.getCreatedAt();
        }

        public static Response getCompaniesResponse(
                Company company) {
            return new Response(company);
        }
    }
}