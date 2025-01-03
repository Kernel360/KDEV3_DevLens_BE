package com.seveneleven.devlens.domain.member.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_password_reset_history")
public class MemberPasswordResetHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 식별 ID

    @Column(name = "member_id", nullable = false)
    private Long memberId; // 회원 ID

    @Column(name = "password", nullable = false, length = 100)
    private String password; // 비밀번호

    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate; // 변경일시

    @Column(name = "changer", nullable = false, length = 100)
    private String changer; // 변경자

    @Column(name = "change_ip", nullable = false, length = 50)
    private String changeIp; // 변경 IP
}