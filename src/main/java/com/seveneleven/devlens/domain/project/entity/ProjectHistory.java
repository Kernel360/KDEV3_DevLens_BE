package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import com.seveneleven.devlens.global.entity.YesNo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_history")
public class ProjectHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 관리 번호

    @Column(nullable = false)
    private String projectName; // 프로젝트명

    @Column(nullable = false)
    private String customerName; // 고객사 이름

    @Column(nullable = false, length = 255)
    private String developerName; // 개발사 이름

    @Column(columnDefinition = "TEXT")
    private String description; // 프로젝트 설명

    @Column(nullable = false)
    private String statusCode; // 프로젝트 상태 코드 (준비, 진행, 완료, 보류, 취소)

    @Column(nullable = false)
    private String typeName; // 프로젝트 유형명

    private Long bnsManagerId; // BNS 담당자 ID

    @Column(nullable = false)
    private YesNo hasImage; // 이미지 파일 유무

    private String contractNumber; // 계약서 번호

    private LocalDateTime plannedStartDate; // 시작 예정일

    private LocalDateTime startDate; // 시작일

    private LocalDateTime plannedEndDate; // 종료 예정일

    private LocalDateTime endDate; // 종료일
}