package com.seveneleven.devlens.domain.admin.dto;

import com.seveneleven.devlens.domain.member.constant.BusinessType;
import com.seveneleven.devlens.domain.member.constant.YN;
import lombok.*;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyDto {
    @Getter
    public static class CompanyResponse {
        private Long id;
        private String companyName;
        private String representativeName;
        private String representativeContact;
        private String representativeEmail;
        private String address;
        private BusinessType businessType;
        private String businessRegistrationNumber;
        private YN representativeImageExists;
        private YN isActive;

        public CompanyResponse(Long id, String companyName, String representativeName, String representativeContact,
                               String representativeEmail, String address, BusinessType businessType, String businessRegistrationNumber, YN representativeImageExists, YN activeStatus) {
            this.id = id;
            this.companyName = companyName;
            this.representativeName = representativeName;
            this.representativeContact = representativeContact;
            this.representativeEmail = representativeEmail;
            this.address = address;
            this.businessType = businessType;
            this.businessRegistrationNumber = businessRegistrationNumber;
            this.representativeImageExists = representativeImageExists;
            this.isActive = activeStatus;
        }
    }

    @Getter
    public static class CompanyRequest {
        private Long id;
        private String companyName;
        private String representativeName;
        private String representativeContact;
        private String representativeEmail;
        private String address;
        private BusinessType businessType;
        private String businessRegistrationNumber;
        private YN representativeImageExists;
        private YN isActive;

        public CompanyRequest(Long id, String companyName, String representativeName, String representativeContact, String representativeEmail, String address, BusinessType businessType, String businessRegistrationNumber, YN representativeImageExists, YN activeStatus) {
            this.id = id;
            this.companyName = companyName;
            this.representativeName = representativeName;
            this.representativeContact = representativeContact;
            this.representativeEmail = representativeEmail;
            this.address = address;
            this.businessType = businessType;
            this.businessRegistrationNumber = businessRegistrationNumber;
            this.representativeImageExists = representativeImageExists;
            this.isActive = activeStatus;
        }
    }
}
