package com.seveneleven.devlens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DevLensApplication {

    public static void main(String[] args) {
        System.out.println("\"소나큐브 테스트\" = " + "소나큐브 테스트");
        SpringApplication.run(DevLensApplication.class, args);
    }

}
