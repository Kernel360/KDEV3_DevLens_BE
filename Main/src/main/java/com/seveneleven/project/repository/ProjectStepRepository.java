package com.seveneleven.project.repository;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectStep;
import com.seveneleven.project.dto.GetProjectDetail;
import com.seveneleven.project.dto.GetProjectStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectStepRepository extends JpaRepository<ProjectStep, Long> {

    Optional<ProjectStep> findByIdAndIsActive(Long id, YesNo isActive);

    // TODO - 한번의 쿼리문으로 합칠 수 있을까?
    List<ProjectStep> findByProjectIdAndIsActive(Long projectId, YesNo isActive);

    @Query("SELECT new com.seveneleven.project.dto.GetProjectDetail$ProjectStepInfo(p_s.id, p_s.stepName, COUNT(CASE WHEN c.isChecked = 'Y' THEN 1 END) * 100.0 / COUNT(*)) " +
            "FROM ProjectStep p_s " +
            "JOIN Checklist c ON p_s.id = c.projectStep.id " +
            "WHERE p_s.project.id = :projectId " +
            "GROUP BY p_s.id " +
            "ORDER BY p_s.stepOrder")
    List<GetProjectDetail.ProjectStepInfo> findStepProcessRate(@Param("projectId") Long projectId);

    @Query("""
    SELECT
        p_c.id,
        p_c.title,
        p_c.isChecked,
        p_c.approvalDate
    FROM
        Checklist p_c
    WHERE
        p_c.projectStep.id = :stepId
    """)
    List<GetProjectStep.ProjectChecklist> findProjectStepChecklist(@Param("stepId") Long stepId);
}
