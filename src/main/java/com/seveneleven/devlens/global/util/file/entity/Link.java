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
@Table(name = "link")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id; // 통합 링크 ID

    @Column(name = "link_type_code", nullable = false)
    private String linkTypeCode; // 첨부 유형 코드

    @Column(name = "reference_id", nullable = false)
    private Long referenceId; // 참조 ID

    @CreatedBy
    @Column(name = "written_by", nullable = false)
    private Long writtenBy; // 등록자 ID

    @Column(name = "written_at", nullable = false)
    private LocalDateTime writtenAt; // 등록 일시

    @Column(name = "link", length = 1000)
    private String link; // 링크

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    private Long createdBy; // 최초 작성자 ID

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 최초 등록 일시
}
