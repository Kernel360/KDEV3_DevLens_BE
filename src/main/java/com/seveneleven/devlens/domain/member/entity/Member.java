package com.seveneleven.devlens.domain.member.entity;

import com.seveneleven.devlens.domain.project.entity.*;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id; // 회원 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company; // 회사 ID (연관관계)

    @Column(name = "role", nullable = false, length = 50)
    private String role; // 계정 권한 (ROLE_ADMIN, ROLE_SUPER, ROLE_USER)

    @Column(name = "username", nullable = false, length = 50)
    private String username; // 아이디

    @Column(name = "password", nullable = false, length = 100)
    private String password; // 비밀번호

    @Column(name = "status_code", nullable = false, length = 50)
    private String statusCode; // 상태 코드

    @Column(name = "profile_image_exists", nullable = false)
    private Boolean profileImageExists; // 프로필 이미지 유무

    @Column(name = "name", nullable = false, length = 100)
    private String name; // 이름

    @Column(name = "email", nullable = false, length = 100)
    private String email; // 이메일

    @Column(name = "birth_date")
    private LocalDate birthDate; // 생년월일

    @Column(name = "phone_number", length = 20)
    private String phoneNumber; // 전화번호

    @Column(name = "department", length = 100)
    private String department; // 부서명

    @Column(name = "position", length = 100)
    private String position; // 직책
}