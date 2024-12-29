package com.seveneleven.devlens.global.util.notification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "NOTIFICATION")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id; // 알림 ID

    @Column(name = "notification_type_code", nullable = false, length = 255)
    private String notificationTypeCode; // 첨부 유형 코드

    @Column(name = "reference_id")
    private Long referenceId; // 참조 ID

    @Column(name = "recipient_id")
    private Long recipientId; // 알림 대상자 ID

    @Column(name = "sent_at")
    private LocalDateTime sentAt; // 발송 시간

    @Column(name = "is_checked", length = 5)
    private String isChecked; // 확인 여부

    @Column(name = "checked_at")
    private LocalDateTime checkedAt; // 확인 시간

    @Column(name = "notification_content", columnDefinition = "TEXT")
    private String notificationContent; // 알림 내용

    @Column(name = "created_by")
    private Long createdBy; // 최초 작성자 ID

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 최초 등록 일시
}
