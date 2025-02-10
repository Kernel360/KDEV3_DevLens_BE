package com.seveneleven.company.dto;

import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.constant.BusinessType;
import com.seveneleven.entity.member.constant.YN;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PutCompany {
    @Getter
    public static class Request {
        @NotBlank
        private String companyName;
        private String representativeName;
        private String representativeContact;
        private String representativeEmail;
        private String address;
        private BusinessType businessType;
        private String businessRegistrationNumber;

        @Override
        public String toString() {
            return "Request{" +
                    "companyName='" + companyName + '\'' +
                    '}';
        }

        private Request(
                String companyName,
                String representativeName,
                String representativeContact,
                String representativeEmail,
                String address,
                BusinessType businessType,
                String businessRegistrationNumber
        ) {
            this.companyName = companyName;
            this.representativeName = representativeName;
            this.representativeContact = representativeContact;
            this.representativeEmail = representativeEmail;
            this.address = address;
            this.businessType = businessType;
            this.businessRegistrationNumber = businessRegistrationNumber;
        }

        public Company toEntity() {
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

        public Company updateCompany(Company company) {
            return company.updateCompany(
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
        private YN isActive;

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    '}';
        }

        private Response(Company company) {
            id = company.getId();
            companyName = company.getCompanyName();
            representativeName = company.getRepresentativeName();
            representativeContact = company.getRepresentativeContact();
            representativeEmail = company.getRepresentativeEmail();
            address = company.getAddress();
            businessType = company.getBusinessType();
            businessRegistrationNumber = company.getBusinessRegistrationNumber();
            isActive = company.getIsActive();
        }

        public static Response of(Company company) {
            return new Response(company);
        }
    }
}
