package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "project_history")
public class ProjectHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 관리 번호

    @Column(name = "project_name", nullable = false, length = 255)
    private String projectName; // 프로젝트명

    @Column(name = "customer_name", nullable = false, length = 255)
    private String customerName; // 고객사 이름

    @Column(name = "developer_name", nullable = false, length = 255)
    private String developerName; // 개발사 이름

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 프로젝트 설명

    @Column(name = "status_code", nullable = false, length = 50)
    private String statusCode; // 프로젝트 상태 코드 (준비, 진행, 완료, 보류, 취소)

    @Column(name = "type_name", nullable = false, length = 255)
    private String typeName; // 프로젝트 유형명

    @Column(name = "bns_manager_id")
    private Long bnsManagerId; // BNS 담당자 ID

    @Column(name = "has_image", nullable = false)
    private Boolean hasImage; // 이미지 파일 유무

    @Column(name = "contract_number", length = 100)
    private String contractNumber; // 계약서 번호

    @Column(name = "planned_start_date")
    private LocalDateTime plannedStartDate; // 시작 예정일

    @Column(name = "start_date")
    private LocalDateTime startDate; // 시작일

    @Column(name = "planned_end_date")
    private LocalDateTime plannedEndDate; // 종료 예정일

    @Column(name = "end_date")
    private LocalDateTime endDate; // 종료일

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