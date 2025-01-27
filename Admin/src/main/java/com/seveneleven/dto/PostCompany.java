package com.seveneleven.dto;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.BusinessType;
import lombok.Getter;

public class PostCompany {
    @Getter
    public static class Request {
        private String companyName;
        private String representativeName;
        private String representativeContact;
        private String representativeEmail;
        private String address;
        private BusinessType businessType;
        private String businessRegistrationNumber;

        public Company toEntity(){
            return Company.createCompany(
                    companyName,
                    representativeName,
                    representativeContact,
                    representativeEmail,
                    address,
                    businessType,
                    businessRegistrationNumber
            );
        }
    }

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

        private Response(Company company) {
            id = company.getId();
            companyName = company.getCompanyName();
            representativeName = company.getRepresentativeName();
            representativeContact = company.getRepresentativeContact();
            representativeEmail = company.getRepresentativeEmail();
            address = company.getAddress();
            businessType = company.getBusinessType();
            businessRegistrationNumber = company.getBusinessRegistrationNumber();
        }

        public static Response of (Company company) {
            return new Response(company);
        }
    }
}
