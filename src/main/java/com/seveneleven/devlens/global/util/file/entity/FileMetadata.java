package com.seveneleven.devlens.global.util.file.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "file_metadata")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_metadata_id")
    private Long id; // 통합 파일 ID

    @Column(name = "file_type_code", nullable = false)
    private String fileTypeCode; // 첨부 유형 코드 (회원, 프로젝트 등)

    @Column(name = "reference_id", nullable = false)
    private Long referenceId; // 참조 ID

    @CreatedBy
    @Column(name = "written_by", updatable = false, nullable = false)
    private Long writtenBy; // 등록자 ID

    @Column(name = "written_at", updatable = false, nullable = false)
    private LocalDateTime writtenAt; // 등록 일시

    @Column(name = "file_title", length = 300, nullable = false)
    private String fileTitle; // 파일명

    @Column(name = "file_format", length = 50, nullable = false)
    private String fileFormat; // 파일 확장자 (포맷)

    @Column(name = "file_size", precision = 10, nullable = false)
    private Float fileSize; // 파일 크기 (KB 단위)

    @Column(name = "file_path", length = 1000, nullable = false)
    private String filePath; // 파일 경로

    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy; // 최초 작성자 ID

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 최초 등록 일시
}