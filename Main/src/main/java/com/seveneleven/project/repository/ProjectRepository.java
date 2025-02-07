package com.seveneleven.project.repository;

import com.seveneleven.entity.project.Project;
import com.seveneleven.entity.project.constant.ProjectStatusCode;
import com.seveneleven.project.dto.GetProjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByIdAndProjectStatusCodeNot(Long id, ProjectStatusCode projectStatusCode);

    @Query("""
        SELECT p
        FROM Project p
        JOIN ProjectAuthorization p_a
            ON
                p_a.member.id = :memberId AND
                p_a.isActive = 'Y' AND
                p_a.project.id = p.id
        WHERE (:projectStatusCode IS NULL OR p.projectStatusCode = :projectStatusCode)
    """)
    List<Project> findAllProgressingProjects(Long memberId, String projectStatusCode);

    @Query("""
        SELECT p
        FROM Project p
        WHERE
            (
                p.customer.id = :companyId OR
                p.developer.id = :companyId
            ) AND 
            (:step IS NULL OR p.projectStatusCode = :projectStatusCode)
    """)
    List<Project> findAllCompanyProgressingProjects(Long companyId, String projectStatusCode);

    @Query(value = """
        SELECT
        new com.seveneleven.project.dto.GetProjectDetail$ProjectDetail(
                p.id,
                p_t.projectTypeName,
                p.projectName,
                p.projectDescription,
                p.bnsManager
        )
        FROM
            Project p
        LEFT JOIN
            ProjectType p_t ON p_t.id = p.projectType.id
        WHERE
             p.id = :projectId
        """)
    Optional<GetProjectDetail.ProjectDetail> getProjectDetail(@Param("projectId") Long projectId);
}
