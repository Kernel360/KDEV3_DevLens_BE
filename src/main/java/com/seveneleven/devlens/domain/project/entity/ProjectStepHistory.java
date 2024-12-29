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
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "project_step_history")
public class ProjectStepHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 프로젝트 단계 이력 ID

    @Column(name = "project_name", nullable = false, length = 255)
    private String projectName; // 프로젝트명

    @Column(name = "step_name", nullable = false, length = 255)
    private String stepName; // 프로젝트 단계명

    @Column(name = "step_description", columnDefinition = "TEXT")
    private String stepDescription; // 프로젝트 단계 설명

    @Column(name = "step_order", nullable = false)
    private Integer stepOrder; // 단계 순서

    @Column(name = "is_active", nullable = false)
    private Boolean isActive; // 사용 유무

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