package com.seveneleven.project.repository;

import com.seveneleven.entity.project.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTagRepository extends JpaRepository<ProjectTag, Long> {
}
