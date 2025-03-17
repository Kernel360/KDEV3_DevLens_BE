package com.seveneleven.entity.file;

import com.seveneleven.entity.file.constant.FileCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "file_metadata")
public class FileMetadata {
    private static final int MAX_FILE_CATEGORY_NAME_LENGTH = 100;
    private static final int MAX_FILE_DISPLAY_TITLE_LENGTH = 300;
    private static final int MAX_FILE_TITLE_LENGTH = 50;
    private static final int MAX_FILE_FORMAT_LENGTH = 50;
    private static final int FILE_SIZE_PRECISION = 10;
    private static final int MAX_FILE_PATH_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; //통합 파일 ID

    @Column(name = "category", length = MAX_FILE_CATEGORY_NAME_LENGTH, nullable = false)
    @Enumerated(EnumType.STRING)
    //파일 카테고리 (회사이미지, 계정프로필이미지, 프로젝트 정보이미지, 게시뭂 파일, 체크승인요청파일, 체크 승인요청거절사유파일)
    private FileCategory category;

    @Column(name = "reference_id", nullable = false)
    private Long referenceId; //참조 ID

    @Column(name = "display_title", length = MAX_FILE_DISPLAY_TITLE_LENGTH, nullable = false)
    private String displayTitle; //파일명

    @Column(name = "title", length = MAX_FILE_TITLE_LENGTH,  nullable = false)
    private String title; //S3 저장명(UUID)

    @Column(name = "content_type", nullable = false)
    private String contentType; //MIME 타입

    @Column(name = "format", length = MAX_FILE_FORMAT_LENGTH, nullable = false)
    private String fileFormat; //파일 확장자 (포맷)

    @Column(name = "size", precision = FILE_SIZE_PRECISION,  nullable = false)
    private Double fileSize; //파일 크기 (KB 단위)

    @Column(name = "path", length = MAX_FILE_PATH_LENGTH, nullable = false)
    private String filePath; //파일 경로

    //시스템 컬럼
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy; //최초 등록자 ID

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; //최초 등록 일시


    public static FileMetadata registerFile(FileCategory category,
                                            Long referenceId,
                                            String displayTitle,
                                            String title,
                                            String contentType,
                                            String fileFormat,
                                            Double fileSize,
                                            String filePath) {

        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.category = category;
        fileMetadata.referenceId = referenceId;
        fileMetadata.displayTitle = displayTitle;
        fileMetadata.title = title;
        fileMetadata.contentType = contentType;
        fileMetadata.fileFormat = fileFormat;
        fileMetadata.fileSize = fileSize;
        fileMetadata.filePath = filePath;

        return fileMetadata;
    }
}