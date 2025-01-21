package com.seveneleven.util.file.repository;

import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.entity.file.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    Optional<FileMetadata> findByCategoryAndReferenceId(FileCategory category, Long referenceId);

    List<FileMetadata> findAllByCategoryAndReferenceId(FileCategory category, Long referenceId);

    Boolean existsByCategoryAndReferenceId(FileCategory category, Long referenceId);

    Integer countByCategoryAndReferenceId(FileCategory category, Long referenceId);
}
