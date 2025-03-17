package com.seveneleven.entity.project.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Getter
@NoArgsConstructor
public class ChangeLog {
    @Field("table_name")
    private String tableName;  // 변경이 발생한 테이블명

    @Field("operation_type")
    private String operationType;  // INSERT, UPDATE, DELETE 중 하나

    @Field("before")
    private Map<String, Object> before;  // 변경 전 데이터

    @Field("after")
    private Map<String, Object> after;  // 변경 후 데이터
}
