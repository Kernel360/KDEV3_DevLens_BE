package global.util.project.controller;

import com.seveneleven.Main;
import com.seveneleven.config.CustomUserDetailsService;
import com.seveneleven.config.JwtAccessDeniedHandler;
import com.seveneleven.config.JwtAuthenticationEntryPoint;
import com.seveneleven.config.TokenProvider;
import com.seveneleven.entity.member.Company;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.constant.Role;
import com.seveneleven.member.service.MemberService;
import com.seveneleven.project.dto.GetProjectList;
import com.seveneleven.project.repository.CheckRequestRepository;
import com.seveneleven.project.repository.ProjectRepository;
import com.seveneleven.project.repository.ProjectStepRepository;
import com.seveneleven.project.service.ProjectService;
import com.seveneleven.project.service.dashboard.ProjectReader;
import com.seveneleven.util.security.CustomUserDetails;
import com.seveneleven.util.security.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;

import static global.util.project.util.MySecurityTestUtils.customUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Slf4j
public class MyProjectControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;
    @MockitoBean
    private MemberService memberService;
    @MockitoBean
    private ProjectReader projectReader;
    @MockitoBean
    private ProjectRepository projectRepository;
    @MockitoBean
    private ProjectStepRepository projectStepRepository;
    @MockitoBean
    private CheckRequestRepository checkRequestRepository;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;
    @MockitoBean
    private TokenProvider tokenProvider;
    @MockitoBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockitoBean
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private Member mockMember;

    @BeforeEach
    void setUp() {
        // Given: Member 객체를 Mock으로 생성
        mockMember = mock(Member.class);

        when(mockMember.getId()).thenReturn(1L);
        when(mockMember.getLoginId()).thenReturn("testUser");
        when(mockMember.getPassword()).thenReturn("testPassword");
        when(mockMember.getCompany()).thenReturn(mock(Company.class));
        when(mockMember.getRole()).thenReturn(Role.USER);
        when(mockMember.getName()).thenReturn("테스트 사용자");
        when(mockMember.getEmail()).thenReturn("test@example.com");
        when(mockMember.getBirthDate()).thenReturn(LocalDate.of(1990, 1, 1));
        when(mockMember.getPhoneNumber()).thenReturn("010-1234-5678");
        when(mockMember.getDepartment()).thenReturn("개발팀");
        when(mockMember.getPosition()).thenReturn("백엔드 개발자");
    }

    @Test
    void getMyProject_ShouldReturnSuccess() throws Exception {
        // Given
        CustomUserDetails mockUserDetails = new CustomUserDetails(MemberDto.fromEntity(mockMember));
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(mockUserDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        GetProjectList.Response mockResponse = GetProjectList.Response.toDto(Collections.emptyList(), Collections.emptyList());
        when(projectService.getProjectList(any(Long.class))).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(customUser(mockUserDetails))
                        .with(csrf())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray());
    }
}
