package com.seveneleven.devlens.global.util.file.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "FILE_METADATA")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_metadata_id")
    private Long id; // 통합 파일 ID

    @Column(name = "file_type_code", nullable = false, length = 255)
    private String fileTypeCode; // 첨부 유형 코드 (회원, 프로젝트 등)

    @Column(name = "reference_id")
    private Long referenceId; // 참조 ID

    @Column(name = "writed_by")
    private Long writedBy; // 등록자 ID

    @Column(name = "writed_at")
    private LocalDateTime writedAt; // 등록 일시

    @Column(name = "file_title", length = 300)
    private String fileTitle; // 파일명

    @Column(name = "file_format", length = 50)
    private String fileFormat; // 파일 확장자 (포맷)

    @Column(name = "file_size")
    private Float fileSize; // 파일 크기 (KB 단위)

    @Column(name = "file_path", length = 1000)
    private String filePath; // 파일 경로

    @Column(name = "created_by")
    private Long createdBy; // 최초 작성자 ID

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 최초 등록 일시
}