package com.seveneleven.project.repository;

import com.seveneleven.entity.global.YesNo;
import com.seveneleven.entity.project.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminProjectTagRepository extends JpaRepository<ProjectTag, Long> {
    List<ProjectTag> findByProjectIdAndIsActive(Long projectId, YesNo isActive);
}
