package com.seveneleven.project.repository;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectStepRepository extends JpaRepository<ProjectStep, Long> {

    @Query("""
        SELECT
            p_s.stepOrder
        FROM ProjectStep p_s
        WHERE p_s.project.id = :projectId
            AND
              p_s.isActive = :isActive
        order by p_s.stepOrder
    """)
    List<Integer> findAllProjectStepStepOrder(Long projectId, YesNo isActive);

    Optional<ProjectStep> findByIdAndIsActive(Long id, YesNo isActive);

    // TODO - 한번의 쿼리문으로 합칠 수 있을까?
    List<ProjectStep> findByProjectIdAndIsActiveOrderByStepOrder(Long projectId, YesNo isActive);

//    @Query("""
//        SELECT new com.seveneleven.project.dto.GetProjectDetail$ProjectStepInfo(
//                p_s.id,
//                p_s.stepName,
//                COUNT(CASE WHEN c.isChecked = 'Y' THEN 1 END) * 100.0 / COUNT(*)
//            )
//            FROM ProjectStep p_s
//            LEFT JOIN Checklist c ON p_s.id = c.projectStep.id
//            WHERE p_s.project.id = :projectId AND p_s.isActive = :isActive
//            GROUP BY p_s.id
//            ORDER BY p_s.stepOrder
//        """)
//    List<GetProjectDetail.ProjectStepInfo> findStepProcessRate(
//            @Param("projectId") Long projectId,
//            @Param("isActive") YesNo isActive
//    );

    @Query("""
    SELECT p.id
    FROM ProjectStep p
    WHERE p.project.id = :projectId
""")
    List<Long> findAllProjectStepIds(@Param("projectId") Long projectId);
}
