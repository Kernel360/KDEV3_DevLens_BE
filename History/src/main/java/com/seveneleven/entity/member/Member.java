package com.seveneleven.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.Role;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 회원 ID

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false, referencedColumnName = "id")
    private Company company; // 회사 ID (연관관계)

    @Column(name = "role", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Role role; // 계정 권한 (ROLE_ADMIN, ROLE_SUPER, ROLE_USER)

    @Column(name = "login_id", nullable = false, length = 50)
    private String loginId; // 아이디

    @Column(name = "password", nullable = false, length = 100)
    private String password; // 비밀번호

    @Column(name = "status_code", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private MemberStatus status = MemberStatus.ACTIVE; // 상태 코드

    @Column(name = "name", nullable = false, length = 100)
    private String name; // 이름

    @Column(name = "email", nullable = false, length = 100)
    private String email; // 이메일

    @Column(name = "date_of_birth")
    private LocalDate birthDate; // 생년월일

    @Column(name = "phone_number", length = 20)
    private String phoneNumber; // 전화번호

    @Column(name = "department")
    private String department; // 부서명

    @Column(name = "position")
    private String position; // 직책


    // 생성 메서드
    public static Member createMember(String loginId, String password, Company company, Role role, String name, String email,
                                      LocalDate birthDate, String phoneNumber, String department, String position) {
        Member member = new Member();
        member.name         = name;
        member.role         = role;
        member.email        = email;
        member.loginId      = loginId;
        member.password     = password;
        member.company      = company;
        member.birthDate    = birthDate;
        member.phoneNumber  = phoneNumber;
        member.position     = position;
        member.department   = department;
        return member;
    }

    // 업데이트 메서드
    public void updateMember(String name, String email, String phoneNumber, Role role, Company company,
                             String department, String position) {
        this.name               = StringUtils.isNotBlank(name) ? name : this.name;
        this.role               = Objects.nonNull(role) ? role : this.role;
        this.email              = Objects.nonNull(email) ? email : this.email;
        this.company            = Objects.nonNull(company) ? company : this.company;
        this.phoneNumber        = StringUtils.isNotBlank(phoneNumber) ? phoneNumber : this.phoneNumber;
        this.department         = Objects.nonNull(department) ? department : this.department;
        this.position           = Objects.nonNull(position) ? position : this.position;
    }


    // 비밀번호 재설정
    public void resetPassword(String password){
        this.password     = password;
    }

    // 삭제 메서드
    public void deleteMember() {
        this.status = MemberStatus.INACTIVE;
    }

    // 탈퇴 메서드
    public void withDrawMember() {
        this.status = MemberStatus.WITHDRAW;
    }

    // 복구 메서드
    public void restoreMember() {
        this.status = MemberStatus.ACTIVE;
    }

}