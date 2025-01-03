package com.seveneleven.devlens.domain.project.entity;

import com.seveneleven.devlens.domain.member.entity.Member;
import com.seveneleven.devlens.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "check_request")
public class CheckRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 체크 요청 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id", nullable = false)
    private Checklist checklist; // 체크 리스트 ID

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate; // 체크 요청 일시

    @Column(name = "approval_status", length = 50)
    private String approvalStatus; // 승인 여부(결과)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false, referencedColumnName = "id")
    private Member requesterId; // 요청자 ID

    @Column(name = "request_ip", length = 50)
    private String requestIp; // 요청자 IP

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // 요청 내용

    @Column(name = "has_file", nullable = false)
    private Boolean hasFile; // 파일 유무

    @Column(name = "has_link", nullable = false)
    private Boolean hasLink; // 링크 유무
}