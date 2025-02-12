package com.seveneleven.member.service;

import com.seveneleven.config.TokenProvider;
import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.entity.member.Member;
import com.seveneleven.entity.member.MemberPasswordResetHistory;
import com.seveneleven.entity.member.constant.MemberStatus;
import com.seveneleven.entity.member.constant.YN;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.member.dto.LoginResponse;
import com.seveneleven.member.dto.LoginPost;
import com.seveneleven.member.dto.MemberPatch;
import com.seveneleven.member.repository.CompanyRepository;
import com.seveneleven.member.repository.MemberRepository;
import com.seveneleven.member.repository.PasswordHistoryRepository;
import com.seveneleven.util.GetIpUtil;
import com.seveneleven.util.file.repository.FileMetadataRepository;
import com.seveneleven.util.security.dto.CustomUserDetails;
import com.seveneleven.util.security.dto.TokenResponse;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.security.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final GetIpUtil getIpUtil;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final PasswordHistoryRepository passwordResetHistory;
    private final FileMetadataRepository fileMetadataRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationMngrBuilder;

    /**
     * 함수명 : login
     * 사용자 로그인 처리 메서드. 로그인 ID와 비밀번호를 확인하여 인증하고, JWT 토큰을 생성하여 반환합니다.
     *
     * @param request 로그인 요청 정보 (로그인 ID와 비밀번호 포함)
     * @return 생성된 JWT 토큰
     */
    @Transactional
    public LoginPost.Response login(LoginPost.Request request) {

        Member member = memberRepository.findByLoginIdAndStatus(request.getLoginId(),MemberStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INCORRECT_PASSWORD);
        }

        // Access Token, Refresh Token 생성
        TokenResponse tokens = getToken(request.getLoginId(), request.getPassword());

        Long companyId      = member.getCompany().getId();
        String companyName  = companyRepository.findNameByIdAndIsActive(companyId, YN.Y);

        LoginResponse company = new LoginResponse(member.getLoginId(),member.getName(),member.getEmail(),
                member.getRole(), getProfileImageUrl(member.getId()), companyId, companyName, member.getDepartment(), member.getPosition());

        return new LoginPost.Response(tokens.accessToken(),
                tokenProvider.getAccessTokenExpireTime(),
                tokens.refreshToken(),
                tokenProvider.getRefreshTokenExpireTime(),
                company);
    }

    /**
     * 함수명 : logout
     * 로그아웃 처리 메서드. 주어진 토큰을 검증한 후, Refresh Token을 삭제 합니다.
     *
     * @param token 로그아웃할 사용자 토큰. "Bearer " 접두어가 포함될 수 있습니다.
     */
    @Transactional
    public void logout(String token) {

        // 1. Access Token 검증
        if (Objects.isNull(token) || !tokenProvider.validateToken(token)) {
            throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        // 2. Refresh Token 삭제
        Long memberId = tokenProvider.getMemberId(token); // Access Token에서 사용자 ID 추출
        if (memberId != null) {
            refreshTokenRepository.delete(memberId); // 사용자와 연관된 Refresh Token 삭제
        }

        // 3. 블랙리스트 등록
        Long expiration = tokenProvider.getExpiration(token);
        refreshTokenRepository.setBlackList(token, "access_token", expiration);

    }

    /**
     * 함수명 : resetPassword
     * 회원 비밀번호를 초기화합니다.
     *
     * @param requestDto 비밀번호를 재설정할 회원의 정보.
     * @return 비밀번호 재설정한 회원 LoginId
     */
    @Transactional
    public MemberPatch.Response resetPassword(HttpServletRequest request, CustomUserDetails userDetails, MemberPatch.Request requestDto) {

        if(Objects.isNull(userDetails)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_TOKEN);
        }

        // 1. 회원 조회
        Member member = memberRepository.findByLoginId(userDetails.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 2. 현재 비밀번호 확인
        if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INCORRECT_PASSWORD);
        }

        // 3. 비밀번호 암호화 후 저장
        member.resetPassword(passwordEncoder.encode(requestDto.getNewPassword()));

        // 4. 비밀번호 변경 이력 추가
        String modifierIp = getIpUtil.getIpAddress(request);
        MemberPasswordResetHistory m = MemberPasswordResetHistory.createPwdHistory(
                member.getId(), member.getPassword(), member.getLoginId(), modifierIp
        );
        passwordResetHistory.save(m);


        // 5. 생성된 비밀번호 반환
        return new MemberPatch.Response(userDetails.getLoginId());
    }


    /**
     * 함수명 getProfileImageUrl
     * 파일 조회(단일)
     *
     * @param memberId 회원 id(pk)
     * @return String 프로필 이미지 url 반환
     */
    @Transactional
    public String getProfileImageUrl(Long memberId) {
        // 해당 파일 카테고리와 참조 id(회원 id)로 entity를 가져온다.
        // 파일이 존재하지 않아도 예외를 던지면 안됨.
        FileMetadata fileMetadataEntity = fileMetadataRepository.findByCategoryAndReferenceId(FileCategory.USER_PROFILE_IMAGE, memberId)
                .orElse(null);

        // 프로필 이미지 url 반환
        return Objects.nonNull(fileMetadataEntity)?fileMetadataEntity.getFilePath():null;
    }


    /**
     * 함수명 : getToken
     * 사용자 인증 정보를 확인하고, JWT 토큰을 생성하여 반환합니다.
     *
     * @param loginId 로그인에 사용할 사용자 ID
     * @param pwd     로그인에 사용할 사용자 비밀번호
     * @return 생성된 JWT 토큰
     */
    public TokenResponse getToken(String loginId, String pwd) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginId, pwd);

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationMngrBuilder.getObject().authenticate(authenticationToken);

        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성 후 반환
        TokenResponse tokens = tokenProvider.createTokens(authentication);

        return tokens;
    }

    @Override
    public Member getMember(Long memberId) {
        return memberRepository.findByIdAndStatus(memberId, MemberStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
