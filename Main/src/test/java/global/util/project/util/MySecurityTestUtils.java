package global.util.project.util;

import com.seveneleven.util.security.dto.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@Slf4j
public class MySecurityTestUtils {

    // customUserDetails를 받아서 SecurityContextHolder에 넣고,
    // MockMvc 요청에 적용할 RequestPostProcessor를 리턴
    public static RequestPostProcessor customUser(CustomUserDetails customUserDetails) {
        return request -> {
            // 1. Authentication 생성
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            customUserDetails,      // principal
                            customUserDetails.getPassword(),
                            customUserDetails.getAuthorities()
                    );

            // 2. SecurityContext 생성 후 Authentication 주입
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);

            // 3. ThreadLocal 기반 SecurityContextHolder에 설정
            SecurityContextHolder.setContext(context);

            return request;
        };
    }
}