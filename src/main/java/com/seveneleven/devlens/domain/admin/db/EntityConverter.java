package com.seveneleven.devlens.domain.admin.db;

public interface EntityConverter <DTO,ENTITY>{
    DTO toDTO(ENTITY entity);
    ENTITY toEntity(DTO dto);
}
