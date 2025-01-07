package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import com.seveneleven.devlens.global.entity.YesNo;
import com.seveneleven.devlens.global.entity.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "checklist")
public class Checklist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 체크리스트 ID

    @JoinColumn(name = "project_step_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ProjectStep projectStep; // 프로젝트 단계 ID

    @Column(nullable = false)
    private String title; // 체크리스트 제목

    @Column(columnDefinition = "TEXT")
    private String description; // 체크리스트 설명

    @Column(nullable = false)
    @Convert(converter = YesNoConverter.class)
    private YesNo isActive; // 사용 유무

    @Column(nullable = false)
    @Convert(converter = YesNoConverter.class)
    private YesNo isMandatory; // 체크 유무

    private Long approverId; // 승인자 ID

    private LocalDateTime approvalDate; // 승인 일시
}