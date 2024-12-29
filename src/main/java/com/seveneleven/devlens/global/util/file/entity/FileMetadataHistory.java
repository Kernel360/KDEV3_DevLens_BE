package com.seveneleven.devlens.global.util.file.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "FILE_METADATA_HISTORY")
public class FileMetadataHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_metadata_history_id")
    private Long id; // 통합 파일 이력 ID

    @Column(name = "history_type_code", nullable = false, length = 255)
    private String historyTypeCode; // 이력 유형 코드

    @Column(name = "file_type_code", nullable = false, length = 255)
    private String fileTypeCode; // 첨부 유형 코드

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

    @Column(name = "file_title", length = 300)
    private String fileTitle; // 파일명

    @Column(name = "file_format", length = 50)
    private String fileFormat; // 파일 확장자

    @Column(name = "file_size")
    private Float fileSize; // 파일 크기 (KB)

    @Column(name = "file_path", length = 1000)
    private String filePath; // 파일 경로

    @Column(name = "created_by")
    private Long createdBy; // 최초 작성자 ID

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 최초 등록 일시
}