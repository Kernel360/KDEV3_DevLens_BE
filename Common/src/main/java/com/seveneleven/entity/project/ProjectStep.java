package com.seveneleven.entity.project;

import com.seveneleven.entity.global.BaseEntity;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.global.converter.YesNoConverter;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_step")
public class ProjectStep extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 프로젝트 단계 ID

    @JoinColumn(name = "project_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project; // 프로젝트 ID

    @Column(nullable = false)
    private String stepName; // 프로젝트 단계명

    private String stepDescription; // 프로젝트 단계 설명

    @Column(nullable = false)
    private Integer stepOrder; // 단계 순서

    @Column(nullable = false)
    @Convert(converter = YesNoConverter.class)
    private YesNo isActive; // 사용 유무

    private ProjectStep(Project project, String stepName, String stepDescription, Integer stepOrder) {
        this.project = project;
        this.stepName = stepName;
        this.stepDescription = stepDescription;
        this.stepOrder = stepOrder;
        isActive = YesNo.YES;
    }

    public static ProjectStep create(Project project, String stepName, String description, Integer stepOrder) {
        return new ProjectStep(project, stepName, description, stepOrder);
    }

    public ProjectStep edit(String stepName, String description, Integer stepOrder) {
        this.stepName = stepName;
        this.stepDescription = description;
        this.stepOrder = stepOrder;
        return this;
    }

    public void delete() {
        if(isActive != YesNo.NO) {
            isActive = YesNo.NO;
            return;
        }
        throw new BusinessException(ErrorCode.PROJECT_STEP_ALREADY_DELETED);
    }
}
