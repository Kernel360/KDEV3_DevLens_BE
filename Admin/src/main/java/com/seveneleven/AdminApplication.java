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
                @Server(description = "local Port Admin", url = "http://localhost:8443"),
                @Server(description = "server Admin", url = "https://api.devlens.work/admin")
        }
)
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
