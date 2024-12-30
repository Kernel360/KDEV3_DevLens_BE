package com.seveneleven.devlens.domain.member.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "company")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId; // 회사 ID

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
    private String businessType; // 사업자 유형 (개인/법인)

    @Column(name = "business_registration_number", nullable = false, length = 50)
    private String businessRegistrationNumber; // 사업자번호

    @Column(name = "representative_image_exists", nullable = false)
    private Boolean representativeImageExists; // 대표 이미지 유무

    @Column(name = "active_status", nullable = false)
    private Boolean activeStatus; // 사용 여부
}