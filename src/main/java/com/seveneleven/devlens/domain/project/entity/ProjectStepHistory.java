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
@Table(name = "project_step_history")
public class ProjectStepHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 프로젝트 단계 이력 ID

    @Column(nullable = false)
    private String projectName; // 프로젝트명

    @Column(nullable = false)
    private String stepName; // 프로젝트 단계명

    @Column(columnDefinition = "TEXT")
    private String stepDescription; // 프로젝트 단계 설명

    @Column(nullable = false)
    private Integer stepOrder; // 단계 순서

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNo isActive; // 사용 유무
}