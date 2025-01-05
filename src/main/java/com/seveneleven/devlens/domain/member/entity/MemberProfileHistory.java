package com.seveneleven.devlens.domain.member.entity;

import com.seveneleven.devlens.domain.member.constant.Role;
import com.seveneleven.devlens.domain.member.constant.YN;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_profile_history")
public class MemberProfileHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 회원 프로필 이력 ID

    @Column(name = "member_id", nullable = false)
    private Long memberId; // 회원 ID

    @Column(name = "member_email", nullable = false)
    private String memberEmail; // 회원 이메일

    @Column(name = "company_id", nullable = false)
    private Long companyId; // 회사 ID

    @Column(name = "role", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Role role; // 계정 권한 (ROLE_ADMIN, ROLE_SUPER, ROLE_USER)

    @Column(name = "name", nullable = false, length = 100)
    private String name; // 이름

    @Column(name = "email", nullable = false, length = 100)
    private String email; // 이메일

    @Column(name = "birth_date")
    private LocalDate birthDate; // 생년월일

    @Column(name = "phone_number", length = 20)
    private String phoneNumber; // 전화번호

    @Column(name = "profile_image_exists", nullable = false)
    private YN profileImageExists; // 프로필 이미지 유무

    @Column(name = "department", length = 100)
    private String department; // 부서명

    @Column(name = "position", length = 100)
    private String position; // 직책



    public static MemberProfileHistory createProfileHistory(Member member, String department, String position) {
        MemberProfileHistory history = new MemberProfileHistory();
        history.memberId    = member.getId();
        history.memberEmail = member.getEmail();
        history.companyId   = member.getCompany().getId();
        history.role        = member.getRole();
        history.name        = member.getName();
        history.email       = member.getEmail();
        history.birthDate   = member.getBirthDate();
        history.phoneNumber = member.getPhoneNumber();
        history.department  = department;
        history.position    = position;
        history.profileImageExists = member.getProfileImageExists();
        return history;
    }


}