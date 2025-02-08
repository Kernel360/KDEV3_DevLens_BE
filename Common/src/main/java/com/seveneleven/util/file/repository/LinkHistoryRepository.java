package com.seveneleven.util.file.repository;

import com.seveneleven.entity.file.LinkHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkHistoryRepository extends JpaRepository<LinkHistory, Long> {
}
