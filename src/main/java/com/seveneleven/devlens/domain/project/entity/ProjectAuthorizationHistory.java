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
@Table(name = "project_authorization_history")
public class ProjectAuthorizationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 프로젝트 권한 목록 이력 ID

    @Column(nullable = false, length = 255)
    private String memberName; // 회원 이름

    @Column(nullable = false, length = 255)
    private String projectStepName; // 프로젝트 단계 이름

    @Column(nullable = false, length = 50)
    private String memberType; // 회원 구분 (client, developer)

    @Column(nullable = false, length = 50)
    private String authorizationCode; // 권한 코드

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNo isDeleted; // 삭제 여부
}