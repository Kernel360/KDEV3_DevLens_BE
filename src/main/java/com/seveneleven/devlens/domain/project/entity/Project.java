package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.domain.member.entity.Company;
import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "project")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 프로젝트 ID

    @Column(name = "project_name", nullable = false, length = 255)
    private String projectName; // 프로젝트명

    @JoinColumn(name = "customer_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Company customerId; // 고객사 ID

    @JoinColumn(name = "developer_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Company developerId; // 개발사 ID

    @Column(name = "project_description", length = 500)
    private String projectDescription; // 프로젝트 설명

    @JoinColumn(name = "project_type_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectType projectTypeId; // 프로젝트 유형 ID

    @Column(name = "project_status_code", nullable = false, length = 50)
    private String projectStatusCode; // 프로젝트 상태 코드 (준비, 진행, 완료, 보류, 취소)

    @JoinColumn(name = "bns_manager_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member bnsManagerId; // BNS 담당자 ID

    @Column(name = "has_image", nullable = false)
    private Boolean hasImage; // 이미지 여부

    @Column(name = "contract_number", length = 100)
    private String contractNumber; // 계약서 번호

    @Column(name = "planned_start_date")
    private LocalDateTime plannedStartDate; // 시작 예정일

    @Column(name = "planned_end_date")
    private LocalDateTime plannedEndDate; // 종료 예정일

    @Column(name = "start_date")
    private LocalDateTime startDate; // 시작일

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

    @Column(name = "final_approver", length = 100)
    private String finalApprover; // 최종 결재자

    @Column(name = "final_approval_date")
    private LocalDateTime finalApprovalDate; // 최종 결재일시
}
