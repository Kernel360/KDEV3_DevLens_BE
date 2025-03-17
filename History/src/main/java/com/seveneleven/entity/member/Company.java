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
@Table(name = "company")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 회사 ID

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


    // 생성 메서드
    public static Company createCompany(String companyName, String representativeName, String representativeContact, String representativeEmail,
                                        String address, BusinessType businessType, String businessRegistrationNumber) {
        Company company = new Company();
        company.address = address;
        company.companyName = companyName;
        company.businessType = businessType;
        company.representativeName = representativeName;
        company.representativeEmail = representativeEmail;
        company.representativeContact = representativeContact;
        company.businessRegistrationNumber = businessRegistrationNumber;
        company.isActive = YN.Y;
        return company;
    }

    // 수정 메서드
    public Company updateCompany(String companyName, String representativeName, String representativeContact, String representativeEmail,
                                 YN isActive, String address, BusinessType businessType, String businessRegistrationNumber) {
        this.companyName = companyName;
        this.representativeName = representativeName;
        this.representativeContact = representativeContact;
        this.representativeEmail = representativeEmail;
        this.isActive = isActive;
        this.address = address;
        this.businessType = businessType;
        this.businessRegistrationNumber = businessRegistrationNumber;
        return this;
    }
}