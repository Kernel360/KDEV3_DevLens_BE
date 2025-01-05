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

    @Column(name = "changed_date", nullable = false)
    private LocalDateTime changedDate; // 변경일시

    @Column(name = "changer", nullable = false, length = 100)
    private String changer; // 변경자

    @Column(name = "changed_ip", nullable = false, length = 50)
    private String changedIp; // 변경 IP


    // 생성 메서드
    public static MemberPasswordResetHistory createPwdHistory( Long memberId, String password, String changer, String changedIp ) {
        MemberPasswordResetHistory history = new MemberPasswordResetHistory();
        history.memberId    = memberId;
        history.password    = password;
        history.changer     = changer;
        history.changedIp   = changedIp;
        history.changedDate = LocalDateTime.now();
        return history;
    }

}