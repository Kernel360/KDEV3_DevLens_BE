package global.util.project.controller;

import com.seveneleven.Main;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
//@ContextConfiguration(initializers = MyEnvUtils.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
public class MyProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${test.loginid}")
    private String loginId;
    @Value("${test.loginpwd}")
    private String password;

    private Cookie accessCookie;
    private Cookie refreshCookie;

    @BeforeEach
    public void setUp() throws Exception {
        String loginRequestJson = "{\"loginId\":\"" + loginId + "\", \"password\":\"" + password + "\"}";

        // 로그인 API 호출 (POST /api/login)
        MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // 응답 헤더에 설정된 Set-Cookie 값에서 토큰을 추출합니다.
        accessCookie = loginResult.getResponse().getCookie("X-Access-Token");
        refreshCookie = loginResult.getResponse().getCookie("X-Refresh-Token");
    }

    @Test
    void getMyProject_ShouldReturnSuccess() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(accessCookie, refreshCookie)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.myProjects").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.companyProjects").isArray());
    }
}
