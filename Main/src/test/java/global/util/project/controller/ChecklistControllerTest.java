package global.util.project.controller;

import com.seveneleven.Main;
import com.seveneleven.entity.global.YesNo;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.project.dto.GetStepChecklist;
import com.seveneleven.project.service.checklist.ChecklistReader;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.response.SuccessCode;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
//@ContextConfiguration(initializers = MyEnvUtils.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
public class ChecklistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChecklistReader checklistReader;

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
    @DisplayName("Get Step's Checklist Test - Success")
    void getStepChecklist_ShouldReturnSuccess() throws Exception {
        // given
        Long stepId = 1L;

        GetStepChecklist.Response response = GetStepChecklist.Response.toDto(
                        stepId,
                        createMockChecklists()
        );

        // 목 객체가 호출 시 미리 정의한 응답을 반환하도록 설정
        when(checklistReader.getStepChecklist(eq(stepId))).thenReturn(response);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/steps/{stepId}/checklists", stepId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(accessCookie, refreshCookie)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(SuccessCode.OK.getStatusCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.checklists").exists());
    }

    @Test
    @DisplayName("Get Step's Checklist Test - Not Exist Step")
    void getStepChecklistNotExistsStep() throws Exception {
        // given
        Long stepId = 213131232313L;  // 존재하지 않는 stepId

        // 목 객체가 해당 stepId에 대해 예외를 던지도록 설정
        when(checklistReader.getStepChecklist(eq(stepId)))
                .thenThrow(new BusinessException(ErrorCode.NOT_FOUND_PROJECT_STEP));

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/steps/{stepId}/checklists", stepId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(accessCookie, refreshCookie))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.NOT_FOUND_PROJECT_STEP.getStatusCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorCode.NOT_FOUND_PROJECT_STEP.getMessage()));
    }

    public static List<GetStepChecklist.ProjectChecklist> createMockChecklists() {
        GetStepChecklist.ProjectChecklist checklist1 = new GetStepChecklist.ProjectChecklist(
                1L,
                "체크리스트 항목 1",
                YesNo.YES,
                LocalDateTime.now()
        );

        GetStepChecklist.ProjectChecklist checklist2 = new GetStepChecklist.ProjectChecklist(
                2L,
                "체크리스트 항목 2",
                YesNo.NO,
                LocalDateTime.now().plusMinutes(5)
        );

        GetStepChecklist.ProjectChecklist checklist3 = new GetStepChecklist.ProjectChecklist(
                3L,
                "체크리스트 항목 3",
                YesNo.YES,
                LocalDateTime.now().plusMinutes(10)
        );

        GetStepChecklist.ProjectChecklist checklist4 = new GetStepChecklist.ProjectChecklist(
                4L,
                "체크리스트 항목 4",
                YesNo.NO,
                LocalDateTime.now().plusMinutes(15)
        );

        return Arrays.asList(checklist1, checklist2, checklist3, checklist4);
    }
}
