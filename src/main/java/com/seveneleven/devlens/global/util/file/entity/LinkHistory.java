package com.seveneleven.devlens.global.util.file.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "LINK_HISTORY")
public class LinkHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id; // 통합 링크 이력 ID

    @Column(name = "history_type_code", nullable = false, length = 255)
    private String historyTypeCode; // 이력 유형 코드

    @Column(name = "link_type_code", nullable = false, length = 255)
    private String linkTypeCode; // 첨부 유형 코드

    @Column(name = "reference_identifier", length = 300)
    private String referenceIdentifier; // 참조 구분자

    @Column(name = "writer_email", length = 255)
    private String writerEmail; // 등록자 이메일

    @Column(name = "writer_name", length = 200)
    private String writerName; // 등록자 이름

    @Column(name = "writer_authority", length = 50)
    private String writerAuthority; // 등록자 권한

    @Column(name = "writed_at")
    private LocalDateTime writedAt; // 등록 일시

    @Column(name = "link", length = 1000)
    private String link; // 링크

    @Column(name = "created_by")
    private Long createdBy; // 최초 작성자 ID

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 최초 등록 일시
}
