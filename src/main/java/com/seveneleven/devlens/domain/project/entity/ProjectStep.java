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
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_step")
public class ProjectStep extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id; // 프로젝트 단계 ID

    @JoinColumn(name = "project_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Project projectId; // 프로젝트 ID

    @Column(nullable = false)
    private String stepName; // 프로젝트 단계명

    @Column(length = 500)
    private String stepDescription; // 프로젝트 단계 설명

    @Column(nullable = false)
    private Integer stepOrder; // 단계 순서

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNo useStatus; // 사용 유무
}
