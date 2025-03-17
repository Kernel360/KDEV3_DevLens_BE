package com.seveneleven.entity.project.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "project_history")
@Getter
@NoArgsConstructor
public class ProjectHistoryDocument {

    @Id
    private String id;  // MongoDB의 자동 생성 ID

    @Field("project_id")
    private Long projectId;  // MySQL의 project 테이블의 PK

    @Field("changes")
    private List<ChangeLog> changes;  // 변경 이력 리스트

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("created_by")
    private Long createdBy;

    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Field("updated_by")
    private Long updatedBy;
}
