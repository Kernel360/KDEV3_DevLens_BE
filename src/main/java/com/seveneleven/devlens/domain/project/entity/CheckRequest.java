package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.global.entity.BaseEntity;
import com.seveneleven.devlens.global.entity.YesNo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "check_request")
public class CheckRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 체크 요청 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Checklist checklist; // 체크 리스트 ID

    @Column(nullable = false)
    private LocalDateTime requestDate; // 체크 요청 일시

    @Column(length = 50)
    private String approvalStatus; // 승인 여부(결과)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, referencedColumnName = "id")
    private Member requesterId; // 요청자 ID

    private String requestIp; // 요청자 IP

    @Column(columnDefinition = "TEXT")
    private String description; // 요청 내용

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNo hasFile; // 파일 유무

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNo hasLink; // 링크 유무
}