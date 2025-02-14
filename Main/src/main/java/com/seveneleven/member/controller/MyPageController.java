package com.seveneleven.member.controller;

import com.seveneleven.entity.member.constant.Role;
import com.seveneleven.member.dto.MyPageGetMember;
import com.seveneleven.member.dto.PatchMember;
import com.seveneleven.member.service.MemberFileService;
import com.seveneleven.member.service.MyPageService;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.SuccessCode;
import com.seveneleven.util.file.dto.FileMetadataResponse;
import com.seveneleven.util.security.GetRoleUtil;
import com.seveneleven.util.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
 * 마이페이지 API Controller
 *
 * 핵심 기능 : 회원 상세 조회, 계정 정보 수정, 회원 탈퇴
 * */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MyPageController implements MyPageDocs{

    private final MyPageService myPageService;
    private final MemberFileService memberFileService;

    /**
     * 함수명 : memberDetail
     * 특정 회원의 정보를 조회합니다.
     *
     * @return HTTP 상태 코드 200 OK와 성공 응답 객체(APIResponse<MyPageGetMember>).
     */
    @GetMapping("")
    public ResponseEntity<APIResponse<MyPageGetMember>> memberDetail(@AuthenticationPrincipal CustomUserDetails userDetails) {

        MyPageGetMember response = myPageService.getMember(userDetails.getLoginId());

        return ResponseEntity.status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, response));
    }

    /**
     * 함수명 : updateMember
     * 회원의 정보를 수정합니다.
     *
     * @param requestDto 수정할 정보를 담은 DTO (RequestBody).
     * @return HTTP 상태 코드 200 OK와 성공 응답 객체(APIResponse<PatchMember.Response>).
     */
    @PatchMapping("")
    public ResponseEntity<APIResponse<PatchMember.Response>> updateMember(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                          @RequestBody PatchMember.Request requestDto) {

        PatchMember.Response response = myPageService.updateMember(userDetails.getLoginId(), requestDto);

        return ResponseEntity.status(SuccessCode.UPDATED.getStatus())
                .body(APIResponse.success(SuccessCode.UPDATED, response));
    }


    /**
     * 함수명 : deleteMember
     * 회원 탈퇴 메서드로 상태를 변경합니다.
     *
     * @return HTTP 상태 코드 200 OK와 성공 응답 객체(APIResponse<SuccessCode>).
     */
    @DeleteMapping("")
    public ResponseEntity<APIResponse<SuccessCode>> deleteMember(@AuthenticationPrincipal CustomUserDetails userDetails) {

        myPageService.deleteMember(userDetails.getLoginId());

        return ResponseEntity.status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }


    /**
     * 함수명 : uploadProfileImage
     * 계정 프로필 이미지 업로드. 이미 등록되어 있는 경우 새 이미지로 교체된다.
     * @auth admin, 해당 계정주
     * @param memberId 해당 회원 Id
     * @return ResponseEntity<APIResponse<SuccessCode>> 성공 응답 객체
     */
    @PostMapping(value="/{memberId}/profile-image", consumes = "multipart/form-data")
    public ResponseEntity<APIResponse<SuccessCode>> uploadProfileImage(@PathVariable("memberId") Long memberId,
                                                                       @RequestParam("file") MultipartFile file,
                                                                       @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long uploaderId = userDetails.getId();
        String uploaderRole = GetRoleUtil.getUserRole(userDetails);

        memberFileService.uploadProfileImage(file, memberId, uploaderId, uploaderRole);

        return ResponseEntity
                .status(SuccessCode.CREATED.getStatus())
                .body(APIResponse.success(SuccessCode.CREATED));
    }


    /**
     * 함수명 : getProfileImage
     * 계정 프로필 이미지를 조회합니다.
     *
     * @param memberId 해당 회원 Id
     * @return ResponseEntity<APIResponse<SuccessCode>> 성공 응답 객체
     */
    @GetMapping(value = "/{memberId}/profile-image")
    public ResponseEntity<APIResponse<FileMetadataResponse>> getProfileImage(@PathVariable("memberId") Long memberId) {
        //memberId로 프로필 이미지 조회
        FileMetadataResponse profileImage = memberFileService.getProfileImage(memberId);

        //반환
        return ResponseEntity
                .status(SuccessCode.OK.getStatus())
                .body(APIResponse.success(SuccessCode.OK, profileImage));
    }


    /**
     * 함수명 : deleteProfileImage
     * 계정 프로필 이미지를 삭제합니다.
     * @auth admin, 해당 계정주
     * @param memberId 해당 회원 Id
     * @param userDetails 수행자 정보
     * @return ResponseEntity<APIResponse<SuccessCode>> 성공 응답 객체
     */
    @DeleteMapping(value="/{memberId}/profile-image")
    public ResponseEntity<APIResponse<SuccessCode>> deleteProfileImage(@PathVariable("memberId") Long memberId,
                                                                       @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long uploaderId = userDetails.getId();
        String uploaderRole = GetRoleUtil.getUserRole(userDetails);

        memberFileService.deleteProfileImage(memberId, uploaderId, uploaderRole);

        return ResponseEntity
                .status(SuccessCode.DELETED.getStatus())
                .body(APIResponse.success(SuccessCode.DELETED));
    }
}
