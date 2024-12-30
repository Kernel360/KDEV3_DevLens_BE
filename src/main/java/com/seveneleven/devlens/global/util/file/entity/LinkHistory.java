package com.seveneleven.devlens.global.util.file.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "link_history")
public class LinkHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id; // 통합 링크 이력 ID

    @Column(name = "history_type_code", nullable = false)
    private String historyTypeCode; // 이력 유형 코드

    @Column(name = "link_type_code", nullable = false)
    private String linkTypeCode; // 첨부 유형 코드

    @Column(name = "reference_identifier", length = 300, nullable = false)
    private String referenceIdentifier; // 참조 구분자

    @Column(name = "writer_email", nullable = false)
    private String writerEmail; // 등록자 이메일

    @Column(name = "writer_name", length = 200, nullable = false)
    private String writerName; // 등록자 이름

    @Column(name = "writer_authority", length = 50, nullable = false)
    private String writerAuthority; // 등록자 권한

    @CreatedDate
    @Column(name = "written_at", nullable = false)
    private LocalDateTime writedAt; // 등록 일시

    @Column(name = "link", length = 1000)
    private String link; // 링크

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy; // 최초 작성자 ID

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 최초 등록 일시
}
