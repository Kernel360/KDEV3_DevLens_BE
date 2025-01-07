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
@Table(name = "project_authorization_history")
public class ProjectAuthorizationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 프로젝트 권한 목록 이력 ID

    @Column(nullable = false)
    private String memberName; // 회원 이름

    @Column(nullable = false)
    private String projectStepName; // 프로젝트 단계 이름

    @Column(nullable = false)
    private String memberType; // 회원 구분 (client, developer)

    @Column(nullable = false)
    private String authorizationCode; // 권한 코드

    @Column(nullable = false)
    @Convert(converter = YesNoConverter.class)
    private YesNo isDeleted; // 삭제 여부
}