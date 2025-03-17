package com.seveneleven.entity.file;

import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.entity.file.constant.FileHistoryType;
import com.seveneleven.entity.member.constant.Role;
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
@Table(name = "file_metadata_history")
public class FileMetadataHistory {
    private static final int MAX_REFERENCE_IDENTIFIER_LENGTH = 300;
    private static final int MAX_FILE_DISPLAY_TITLE_LENGTH = 300;
    private static final int MAX_FILE_TITLE_LENGTH = 50;
    private static final int MAX_WRITER_NAME_LENGTH = 200;
    private static final int MAX_WRITER_AUTHORITY_LENGTH = 50;
    private static final int MAX_FILE_FORMAT_LENGTH = 50;
    private static final int FILE_SIZE_PRECISION = 10;
    private static final int MAX_FILE_PATH_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 통합 파일 이력 ID

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FileHistoryType type; // 이력 유형(등록/삭제)

    @Column(name = "file_category", nullable = false)
    @Enumerated(EnumType.STRING)
    // 파일 카테고리(회사로고, 계정프로필이미지, 프로젝트 정보이미지, 게시물파일, 체크승인요청파일, 체크승인요청거절사유파일)
    private FileCategory fileCategory;

    @Column(name = "reference_id", nullable = false)
    private Long referenceId; //참조 ID

    // 회사로고 - 회사명, 계정프로필 - 계정이메일, 게시물 파일 - 게시물 제목
    // 체크승인요청파일 - 요청제목, 체크 승인요청 거절사유 - 요청제목
    @Column(name = "reference_identifier", length = MAX_REFERENCE_IDENTIFIER_LENGTH, nullable = false)
    private String referenceIdentifier; //참조 구분자

    @Column(name = "file_display_title", length = MAX_FILE_DISPLAY_TITLE_LENGTH, nullable = false)
    private String fileDisplayTitle; //파일명

    @Column(name = "file_title", length = MAX_FILE_TITLE_LENGTH, nullable = false)
    private String fileTitle; //S3 저장명(UUID)

    @Column(name = "writer_email", nullable = false)
    private String writerEmail; // 파일 등록자 이메일

    @Column(name = "writer_name", length = MAX_WRITER_NAME_LENGTH, nullable = false)
    private String writerName; // 파일 등록자 이름

    @Column(name = "writer_authority", length = MAX_WRITER_AUTHORITY_LENGTH, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role writerAuthority; // 파일 등록자 권한

    @Column(name = "written_at", nullable = false)
    private LocalDateTime writtenAt; // 파일 등록 일시

    @Column(name = "content_type", nullable = false)
    private String contentType; //MIME 타입

    @Column(name = "file_format", length = MAX_FILE_FORMAT_LENGTH, nullable = false)
    private String fileFormat; // 파일 확장자(포맷)

    @Column(name = "file_size", precision = FILE_SIZE_PRECISION, nullable = false)
    private Double fileSize; // 파일 크기 (KB)

    @Column(name = "file_path", length = MAX_FILE_PATH_LENGTH, nullable = false)
    private String filePath; // 파일 경로

    //시스템컬럼
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy; // 이력 등록자 ID

    @Column(name = "history_registrant_name", nullable = false, updatable = false)
    private String historyRegistrantName; // 이력 등록자 이름

    @Column(name = "history_registrant_email", nullable = false, updatable = false)
    private String historyRegistrantEmail; // 이력 등록자 이메일

    @Column(name = "history_registrant_Authority", nullable = false, updatable = false)
    private Role historyRegistrantAuthority; // 이력 등록자 권한

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 최초 등록 일시

    // 공통 생성 메서드
    private static FileMetadataHistory createHistory(FileHistoryType type,
                                                     FileCategory fileCategory,
                                                     Long referenceId,
                                                     String referenceIdentifier,
                                                     String fileDisplayTitle,
                                                     String fileTitle,
                                                     String writerEmail,
                                                     String writerName,
                                                     Role writerAuthority,
                                                     LocalDateTime writtenAt,
                                                     String contentType,
                                                     String fileFormat,
                                                     Double fileSize,
                                                     String filePath,
                                                     String historyRegistrantName,
                                                     String historyRegistrantEmail,
                                                     Role historyRegistrantAuthority) {

        FileMetadataHistory fileMetadataHistory = new FileMetadataHistory();
        fileMetadataHistory.type = type;
        fileMetadataHistory.fileCategory = fileCategory;
        fileMetadataHistory.referenceId = referenceId;
        fileMetadataHistory.referenceIdentifier = referenceIdentifier;
        fileMetadataHistory.fileDisplayTitle = fileDisplayTitle;
        fileMetadataHistory.fileTitle = fileTitle;
        fileMetadataHistory.writerEmail = writerEmail;
        fileMetadataHistory.writerName = writerName;
        fileMetadataHistory.writerAuthority = writerAuthority;
        fileMetadataHistory.writtenAt = writtenAt;
        fileMetadataHistory.contentType = contentType;
        fileMetadataHistory.fileFormat = fileFormat;
        fileMetadataHistory.fileSize = fileSize;
        fileMetadataHistory.filePath = filePath;
        fileMetadataHistory.historyRegistrantName = historyRegistrantName;
        fileMetadataHistory.historyRegistrantEmail = historyRegistrantEmail;
        fileMetadataHistory.historyRegistrantAuthority = historyRegistrantAuthority;

        return fileMetadataHistory;
    }

    // 파일 등록 이력 생성
    public static FileMetadataHistory createRegisterHistory(FileCategory fileCategory,
                                                            Long referenceId,
                                                            String referenceIdentifier,
                                                            String fileDisplayTitle,
                                                            String fileTitle,
                                                            String writerEmail,
                                                            String writerName,
                                                            Role writerAuthority,
                                                            LocalDateTime writtenAt,
                                                            String contentType,
                                                            String fileFormat,
                                                            Double fileSize,
                                                            String filePath,
                                                            String historyRegistrantName,
                                                            String historyRegistrantEmail,
                                                            Role historyRegistrantAuthority
    ) {
        return createHistory(FileHistoryType.REGISTER,
                fileCategory, referenceId, referenceIdentifier,
                fileDisplayTitle, fileTitle, writerEmail, writerName,
                writerAuthority, writtenAt, contentType, fileFormat, fileSize, filePath,
                historyRegistrantName, historyRegistrantEmail, historyRegistrantAuthority);
    }

    // 파일 삭제 이력 생성
    public static FileMetadataHistory createDeleteHistory(FileCategory fileCategory,
                                                          Long referenceId,
                                                          String referenceIdentifier,
                                                          String fileDisplayTitle,
                                                          String fileTitle,
                                                          String writerEmail,
                                                          String writerName,
                                                          Role writerAuthority,
                                                          LocalDateTime writtenAt,
                                                          String contentType,
                                                          String fileFormat,
                                                          Double fileSize,
                                                          String filePath,
                                                          String historyRegistrantName,
                                                          String historyRegistrantEmail,
                                                          Role historyRegistrantAuthority
    ) {
        return createHistory(FileHistoryType.DELETE,
                fileCategory, referenceId, referenceIdentifier,
                fileDisplayTitle, fileTitle, writerEmail, writerName,
                writerAuthority, writtenAt, contentType, fileFormat, fileSize, filePath,
                historyRegistrantName, historyRegistrantEmail, historyRegistrantAuthority);
    }

}