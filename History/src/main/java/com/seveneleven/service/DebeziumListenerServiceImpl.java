package com.seveneleven.service;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class DebeziumListenerServiceImpl {

    private final MongoTemplate mongoTemplate;
    private final Executor executor;
    private DebeziumEngine<ChangeEvent<String, String>> engine;

    public DebeziumListenerServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.executor = Executors.newSingleThreadExecutor();
    }

    @PostConstruct
    public void start() {
        ResourceBundle bundle = ResourceBundle.getBundle("debezium_local");
        Properties props = new Properties();

        bundle.keySet().forEach(key -> props.put(key, bundle.getString(key)));

        // ✅ Debezium 설정값 로그 출력
        log.info("🔍 Debezium 설정값 확인:");
        props.forEach((key, value) -> log.info("{} = {}", key, value));

        // ✅ Kafka 관련 설정값 확인
        log.info("🔍 Kafka 설정값 확인:");
        log.info("KAFKA_BOOTSTRAP_SERVERS (환경 변수) = {}", System.getenv("KAFKA_BOOTSTRAP_SERVERS"));
        log.info("bootstrap.servers (Debezium 설정) = {}", props.getProperty("offset.storage.bootstrap.servers"));

        // ✅ 환경 변수 직접 확인 (윈도우 터미널과 일치하는지 비교)
        System.out.println("🔍 System.getenv(\"KAFKA_BOOTSTRAP_SERVERS\"): " + System.getenv("KAFKA_BOOTSTRAP_SERVERS"));
        System.out.println("🔍 props.getProperty(\"bootstrap.servers\"): " + props.getProperty("bootstrap.servers"));

        // ✅ Debezium 엔진 시작
        engine = DebeziumEngine.create(Json.class)
                .using(props)
                .notifying(this::handleChangeEvent)
                .build();

        executor.execute(engine);
    }

    private void handleChangeEvent(ChangeEvent<String, String> changeEvent) {
        System.out.println("Received change event: " + changeEvent);

        String data = changeEvent.value();
        if (data != null) {
            mongoTemplate.save(data, "debezium_events");
        }
    }
}
