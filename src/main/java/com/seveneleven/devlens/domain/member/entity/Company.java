package com.seveneleven.devlens.domain.member.entity;

import com.seveneleven.devlens.domain.member.constant.BusinessType;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "representative_image_exists", nullable = false)
    @Enumerated(EnumType.STRING)
    private YN representativeImageExists = YN.N; // 대표 이미지 유무

    @Column(name = "is_active", nullable = false)
    @Enumerated(EnumType.STRING)
    private YN isActive = YN.Y; // 사용 여부
}