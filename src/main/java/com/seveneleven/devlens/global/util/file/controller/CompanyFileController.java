package com.seveneleven.devlens.global.util.file.controller;

import com.seveneleven.devlens.global.response.APIResponse;
import com.seveneleven.devlens.global.util.file.Service.CompanyFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/company/files")
@RequiredArgsConstructor
public class CompanyFileController {
    private final CompanyFileService companyFileService;

    /**
     * 1. 회사 로고 이미지 업로드
     * @param file 업로드할 이미지 파일
     * @return 업로드된 파일 메타데이터, 성공 메시지
     */
    @PostMapping(value = "", consumes = "multipart/form-data")
    @Operation(
            summary = "Upload a file",
            description = "Upload a image for company logo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid file upload request")
            }
    )
    public ResponseEntity<Object> uploadFile(@RequestParam("file")
                                             @Schema(type = "string", format = "binary", description = "File to upload") MultipartFile file,
                                             @RequestParam("company")
                                             @Schema(type = "Long", description = "Company to upload") Long companyId) throws Exception {
        //TODO) 토큰에서 uploader 정보 가져오기
        Long uploaderId = 1L;

        APIResponse uploadResponse = companyFileService.uploadLogoImage(file, companyId, uploaderId);

        return ResponseEntity.status(uploadResponse.getCode()).body(uploadResponse);
    }

    /**
     * 2. 회사 로고 조회
     * @param companyId 해당 회사 id
     * @return 해당 회사의 로고 이미지 메타데이터
     */
    @GetMapping("")
    public ResponseEntity<APIResponse> getCompanyLogo(@RequestParam() Long companyId) throws Exception {
        APIResponse filesResponse = companyFileService.getLogoImage(companyId);

        return ResponseEntity.status(filesResponse.getCode()).body(filesResponse);
    }

    /**
     * 3. 회사 로고 삭제
     * @param fileId 삭제할 파일 id
     * @return 성공 메시지
     */
    @DeleteMapping("")
    public ResponseEntity<APIResponse> deleteCompanyLogo(@RequestParam("fileId") Long fileId) throws Exception {
        APIResponse deleteResponse = companyFileService.deleteLogo(fileId);

        return ResponseEntity.status(deleteResponse.getCode()).body(deleteResponse);
    }

}
