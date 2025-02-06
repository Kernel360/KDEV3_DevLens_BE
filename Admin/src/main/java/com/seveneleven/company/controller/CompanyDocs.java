package com.seveneleven.company.controller;

import com.seveneleven.company.dto.*;
import com.seveneleven.project.dto.GetProject;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Administrator Company Management API", description = "API for administrators to manage companies")
@RequestMapping("/api/admin/companies")
public interface CompanyDocs {

    @PostMapping("")
    @Operation(
            summary = "회사 생성",
            description = "회사 생성",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "성공적으로 회사를 생성했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostCompany.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<PostCompany.Response>> createCompany(@RequestBody PostCompany.Request companyRequest);

    @GetMapping("/{id}")
    @Operation(
            summary = "회사 상세 정보",
            description = "회사 상세 정보 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 회사 상세 정보를 조회했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetCompanyDetail.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "회사 id",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<GetCompanyDetail.Response>> readCompany(
            @PathVariable Long id
    );

    @GetMapping("/{id}")
    @Operation(
            summary = "회사 참여 프로젝트",
            description = "회사 참여 프로젝트",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 회사 참여 프로젝트를 조회했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "회사 id",
                            required = true,
                            example = "1"
                    ),
                    @Parameter(
                            name = "page",
                            description = "프로젝트 페이지 번호",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PaginatedResponse<GetProject.Response>>> readCompanyProject(
            @PathVariable Long id,
            @RequestParam Integer page
    );

    @GetMapping("")
    @Operation(
            summary = "회사 목록 조회",
            description = "회사 목록 페이지 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 회사 목록 페이지를 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "페이지 번호",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PaginatedResponse<GetCompanies.Response>>> readCompanyList(@RequestParam(value = "page") Integer page);

    @GetMapping("/search")
    @Operation(
            summary = "회사 검색",
            description = "회사 이름으로 회사 검색",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 회사 이름을 조회했습니다",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "name",
                            description = "회사 이름",
                            required = true,
                            example = "카카오"
                    ),
                    @Parameter(
                            name = "page",
                            description = "목록 페이지",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PaginatedResponse<GetCompanies.Response>>> searchCompaniesByName(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page") Integer page
    );

    @PutMapping("/{id}")
    @Operation(
            summary = "회사 수정",
            description = "회사 상세 정보 수정",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회사 정보를 성공적으로 수정했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PutCompany.Response.class)
                            )
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "회사 id",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<PutCompany.Response>> updateCompany(
            @PathVariable Long id,
            @RequestBody PutCompany.Request request
    );

    @DeleteMapping("/{id}")
    @Operation(
            summary = "회사 삭제",
            description = "회사 삭제",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 회사를 삭제했습니다."
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "회사 id",
                            required = true,
                            example = "1"
                    )
            }
    )
    ResponseEntity<APIResponse<Object>> deleteCompany(@PathVariable Long id);

    @GetMapping("/all")
    @Operation(
            summary = "전체 회사 조회",
            description = "전체 회사 리스트 조회",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공적으로 전체 회사를 반환했습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = List.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<List<GetAllCompanies>>> readAllCompany();

    @GetMapping("/{companyId}/members")
    @Operation(
            summary = "회사 소속 회원 전체 조회",
            description = "회사에 소속된 모든 회원을 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "회사의 회원들이 성공적으로 조회되었습니다.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetCompanyMember.Response.class)
                            )
                    )
            }
    )
    ResponseEntity<APIResponse<GetCompanyMember.Response>> getCompanyMembers(
            @Parameter(description = "조회할 회사의 ID") @PathVariable Long companyId
    );
}
