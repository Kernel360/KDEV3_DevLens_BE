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
@Table(name = "LINK")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id; // 통합 링크 ID

    @Column(name = "link_type_code", nullable = false, length = 255)
    private String linkTypeCode; // 첨부 유형 코드

    @Column(name = "reference_id")
    private Long referenceId; // 참조 ID

    @Column(name = "writed_by")
    private Long writedBy; // 등록자 ID

    @Column(name = "writed_at")
    private LocalDateTime writedAt; // 등록 일시

    @Column(name = "link", length = 1000)
    private String link; // 링크

    @Column(name = "created_by")
    private Long createdBy; // 최초 작성자 ID

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 최초 등록 일시
}
