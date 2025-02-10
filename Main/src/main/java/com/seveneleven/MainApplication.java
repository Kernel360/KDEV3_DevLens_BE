package com.seveneleven;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(
        info = @Info(
                description = "Devlens Admin API 문서",
                title = "Devlens Admin API 문서",
                version = "1.0"
        ),
        servers = {
                @Server(description = "local Port Main", url = "http://localhost:8444"),
                @Server(description = "server Main", url = "https://api.devlens.work/main")
        }
)
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}