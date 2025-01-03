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
@Table(name = "project_type")
public class ProjectType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_type_id")
    private Long Id; // 프로젝트 유형 ID

    @Column(nullable = false)
    private String projectTypeName; // 프로젝트 유형명

    @Column(length = 500)
    private String projectTypeDescription; // 프로젝트 유형 설명

    @Column(nullable = false)
    private YesNo useStatus; // 사용 유무
}
