package com.seveneleven.entity.file;

import com.seveneleven.entity.file.constant.LinkCategory;
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
@Table(name = "link")
public class Link {
    private static final int MAX_LINK_TITLE_LENGTH = 400;
    private static final int MAX_LINK_LENGTH = 4000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; //통합 링크 ID

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    //링크 카테고리 - 승인요청, 승인거절사유, 게시물
    private LinkCategory category;

    @Column(name = "reference_id", nullable = false)
    private Long referenceId; //참조 ID

    @Column(name = "link_title", length = MAX_LINK_TITLE_LENGTH)
    private String linkTitle; //링크명

    @Column(name = "link", length = MAX_LINK_LENGTH, nullable = false)
    private String link; //링크

    //시스템 컬럼
    @CreatedBy
    @Column(name = "created_by", nullable = false)
    private Long createdBy; //최초 등록자 ID

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; //최초 등록 일시
}
