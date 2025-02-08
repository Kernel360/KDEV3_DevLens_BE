package com.seveneleven.entity.file;

import com.seveneleven.entity.file.constant.LinkCategory;
import com.seveneleven.entity.file.constant.LinkHistoryType;
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
@Table(name = "link_history")
public class LinkHistory {
    private static final int MAX_REFERENCE_IDENTIFIER_LENGTH = 300;
    private static final int MAX_WRITER_NAME_LENGTH = 200;
    private static final int MAX_WRITER_AUTHORITY_LENGTH = 50;
    private static final int MAX_LINK_TITLE_LENGTH = 100;
    private static final int MAX_LINK_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 통합 링크 이력 ID

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LinkHistoryType type; // 이력 유형(등록/삭제)

    @Column(name = "link_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private LinkCategory linkCategory; //링크 카테고리

    @Column(name = "reference_id", nullable = false)
    private Long referenceId; //참조 ID

    // 게시물 - 게시물 제목, 체크승인요청 - 요청제목, 체크 승인요청 거절 - 요청제목
    @Column(name = "reference_identifier", length = MAX_REFERENCE_IDENTIFIER_LENGTH, nullable = false)
    private String referenceIdentifier; // 참조 구분자

    @Column(name = "writer_email", nullable = false)
    private String writerEmail; // 등록자 이메일

    @Column(name = "writer_name", length = MAX_WRITER_NAME_LENGTH, nullable = false)
    private String writerName; // 등록자 이름

    @Column(name = "writer_authority", length = MAX_WRITER_AUTHORITY_LENGTH, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role writerAuthority; // 등록자 권한

    @Column(name = "written_at", nullable = false)
    private LocalDateTime writtenAt; // 링크 등록 일시

    @Column(name = "link_title", length = MAX_LINK_TITLE_LENGTH)
    private String linkTitle; //링크명

    @Column(name = "link", length = MAX_LINK_LENGTH, nullable = false)
    private String link; // 링크

    //시스템 컬럼
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
    private LocalDateTime createdAt; // 이력 등록 일시

    //공통 생성 메서드
    private static LinkHistory createHistory(LinkHistoryType type,
                                             LinkCategory linkCategory,
                                             Long referenceId,
                                             String referenceIdentifier,
                                             String writerEmail,
                                             String writerName,
                                             Role writerAuthority,
                                             LocalDateTime writtenAt,
                                             String linkTitle,
                                             String link,
                                             String historyRegistrantName,
                                             String historyRegistrantEmail,
                                             Role historyRegistrantAuthority){

        LinkHistory history = new LinkHistory();
        history.type = type;
        history.linkCategory = linkCategory;
        history.referenceId = referenceId;
        history.referenceIdentifier = referenceIdentifier;
        history.writerEmail = writerEmail;
        history.writerName = writerName;
        history.writerAuthority = writerAuthority;
        history.writtenAt = writtenAt;
        history.linkTitle = linkTitle;
        history.link = link;
        history.historyRegistrantName = historyRegistrantName;
        history.historyRegistrantEmail = historyRegistrantEmail;
        history.historyRegistrantAuthority = historyRegistrantAuthority;

        return history;
    }

    //파일 등록 이력 생성
    public static LinkHistory createRegisterHistory(LinkCategory linkCategory,
                                                    Long referenceId,
                                                    String referenceIdentifier,
                                                    String writerEmail,
                                                    String writerName,
                                                    Role writerAuthority,
                                                    LocalDateTime writtenAt,
                                                    String linkTitle,
                                                    String link,
                                                    String historyRegistrantName,
                                                    String historyRegistrantEmail,
                                                    Role historyRegistrantAuthority
    ) {

        return createHistory(LinkHistoryType.REGISTER,
                linkCategory, referenceId, referenceIdentifier,
                writerEmail, writerName, writerAuthority, writtenAt, linkTitle, link,
                historyRegistrantName, historyRegistrantEmail, historyRegistrantAuthority);
    }

    //파일 삭제 이력 생성
    public static LinkHistory createDeleteHistory(LinkCategory linkCategory,
                                                  Long referenceId,
                                                  String referenceIdentifier,
                                                  String writerEmail,
                                                  String writerName,
                                                  Role writerAuthority,
                                                  LocalDateTime writtenAt,
                                                  String linkTitle,
                                                  String link,
                                                  String historyRegistrantName,
                                                  String historyRegistrantEmail,
                                                  Role historyRegistrantAuthority
    ){
        return createHistory(LinkHistoryType.DELETE,
                linkCategory, referenceId, referenceIdentifier,
                writerEmail, writerName, writerAuthority, writtenAt, linkTitle, link,
                historyRegistrantName, historyRegistrantEmail, historyRegistrantAuthority);
    }
}
