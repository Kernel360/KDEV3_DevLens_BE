package com.seveneleven.dto;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.BusinessType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCompanyDetail {
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
        }

        public static Response of(
                Company company
        ) {
            return new Response(company);
        }
    }
}
