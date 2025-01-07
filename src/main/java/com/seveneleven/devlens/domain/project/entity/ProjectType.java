package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.global.entity.BaseEntity;
import com.seveneleven.devlens.global.entity.YesNo;
import com.seveneleven.devlens.global.entity.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_type")
public class ProjectType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id; // 프로젝트 유형 ID

    @Column(nullable = false)
    private String projectTypeName; // 프로젝트 유형명

    private String projectTypeDescription; // 프로젝트 유형 설명

    @Convert(converter = YesNoConverter.class)
    private YesNo useStatus; // 사용 유무
}
