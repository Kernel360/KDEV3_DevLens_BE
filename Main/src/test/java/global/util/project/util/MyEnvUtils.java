package global.util.project.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MyEnvUtils implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String ENV_FILE = ".env";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 현재 프로젝트 루트 경로를 가져옵니다.
        String projectRootPath = System.getProperty("user.dir");
        log.info("현재 작업 디렉토리: {}", projectRootPath);

        // .env 파일 경로 설정
        File envFile = new File(projectRootPath, "../.env").getAbsoluteFile();
        log.info(".env 파일 경로: {}", envFile.getAbsolutePath());

        // .env 파일 존재 여부 확인
        if (!envFile.exists()) {
            log.warn(".env 파일을 찾을 수 없습니다: {}", envFile.getAbsolutePath());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(envFile, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // 빈 줄 또는 주석 무시

                int idx = line.indexOf('=');
                if (idx < 0) {
                    log.warn("잘못된 .env 라인 형식: {}", line);
                    continue;
                }

                String key = line.substring(0, idx).trim();
                String value = line.substring(idx + 1).trim();

                if (System.getProperty(key) == null) {
                    System.setProperty(key, value);
                }
            }
        } catch (Exception e) {
            log.error(".env 파일 읽기 중 오류 발생", e);
        }
    }
}
