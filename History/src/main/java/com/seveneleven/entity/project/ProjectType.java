package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.global.converter.YesNoConverter;
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
    private Long id; // 프로젝트 유형 ID

    @Column(nullable = false)
    private String projectTypeName; // 프로젝트 유형명

    private String projectTypeDescription; // 프로젝트 유형 설명

    @Convert(converter = YesNoConverter.class)
    private YesNo useStatus; // 사용 유무

    public ProjectType(String projectTypeName, String projectTypeDescription) {
        this.projectTypeName = projectTypeName;
        this.projectTypeDescription = projectTypeDescription;
        this.useStatus = YesNo.YES;
    }
}
