package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.global.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "check_result_history")
public class CheckResultHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID

    @Column(columnDefinition = "TEXT")
    private String description; // 요청 내용

    private String approvalStatus; // 승인 여부(결과)

    @Column(nullable = false)
    private Long processorId; // 처리자 ID

    private String processorIp; // 처리자 IP

    @Column(columnDefinition = "TEXT")
    private String rejectionReason; // 거부 사유

    @Column(nullable = false)
    @Convert(converter = YesNoConverter.class)
    private YesNo isActive; // 사용 유무
}