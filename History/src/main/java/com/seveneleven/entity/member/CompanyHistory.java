package com.seveneleven.entity.member;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.member.constant.BusinessType;
import com.seveneleven.entity.member.constant.YN;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "company_history")
public class CompanyHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 관리 번호

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName; // 회사명

    @Column(name = "representative_name", nullable = false, length = 100)
    private String representativeName; // 대표자명

    @Column(name = "representative_contact", length = 20)
    private String representativeContact; // 대표 번호

    @Column(name = "representative_email", length = 100)
    private String representativeEmail; // 대표 이메일

    @Column(name = "address", length = 500)
    private String address; // 주소

    @Column(name = "business_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private BusinessType businessType; // 사업자 유형 (개인/법인)

    @Column(name = "business_registration_number", nullable = false, length = 50)
    private String businessRegistrationNumber; // 사업자번호

    @Column(name = "is_active", nullable = false)
    @Enumerated(EnumType.STRING)
    private YN isActive = YN.Y; // 사용 여부

    private CompanyHistory(Company company){
        companyName = company.getCompanyName();
        representativeName = company.getRepresentativeName();
        representativeContact = company.getRepresentativeContact();
        representativeEmail = company.getRepresentativeEmail();
        address = company.getAddress();
        businessType = company.getBusinessType();
        businessRegistrationNumber = company.getBusinessRegistrationNumber();
        isActive = company.getIsActive();
    }

    public static CompanyHistory of(Company company){
        return new CompanyHistory(company);
    }
}
