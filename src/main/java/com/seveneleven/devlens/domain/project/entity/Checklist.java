package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "checklist")
@EntityListeners(AuditingEntityListener.class)
public class Checklist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 체크리스트 ID

    @JoinColumn(name = "project_step_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectStep projectStep; // 프로젝트 단계 ID

    @Column(name = "title", nullable = false, length = 255)
    private String title; // 체크리스트 제목

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 체크리스트 설명

    @Column(name = "is_active", nullable = false)
    private Boolean isActive; // 사용 유무

    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory; // 체크 유무

    @Column(name = "approver", length = 100)
    private String approver; // 승인자 ID

    @Column(name = "approval_date")
    private LocalDateTime approvalDate; // 승인 일시

    @CreatedDate
    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate; // 등록일시

    @LastModifiedDate
    @Column(name = "modification_date")
    private LocalDateTime modificationDate; // 수정일시

    @CreatedBy
    @Column(name = "registered_by", length = 100)
    private String registeredBy; // 등록자

    @LastModifiedBy
    @Column(name = "modified_by", length = 100)
    private String modifiedBy; // 수정자
}