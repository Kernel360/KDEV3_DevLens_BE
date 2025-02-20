package com.seveneleven.board.service;

import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.dto.FileMetadataResponse;
import com.seveneleven.util.file.handler.FileHandler;
import com.seveneleven.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostFileServiceImpl implements PostFileService {
    private final FileHandler fileHandler;
    private final PostRepository postRepository;
    private final FileMetadataRepository fileMetadataRepository;
    private final PostFileHistoryService postFileHistoryService;

    private static final int MAX_FILE_COUNT = 10; //게시물별 최대 파일 수(10개)

    /**
     * 1. 게시물 파일 업로드
     * 함수명 : uploadPostFiles
     * @auth admin, 게시물 작성자
     * @param files 업로드할 로고 이미지 파일들
     * @param postId 해당 게시물 id
     * @param uploaderId 업로드 수행자 id
     */
    @Override
    @Transactional
    public List<FileMetadataResponse> uploadPostFiles(List<MultipartFile> files, Long postId, Long uploaderId, String uploaderRole){
        //1. 게시물 id로 존재여부 판별
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //2. 수행자 권한 판별 - admin, 해당 게시글 작성자
        //계정 본인이 아니거나 admin이 아닌경우 예외발생
        if(!(postEntity.getCreatedBy().equals(uploaderId) || "ADMIN".equals(uploaderRole))){
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        //3. 저장할 파일 갯수 + 현재 저장 갯수 >= 11인지 판별
        //현재 저장된 파일 갯수 확인
        Integer currentFileCnt = fileMetadataRepository.countByCategoryAndReferenceId(FileCategory.POST_ATTACHMENT, postEntity.getId());
        if(currentFileCnt + files.size() > MAX_FILE_COUNT){
            throw new BusinessException(ErrorCode.FILE_QUANTITY_EXCEED_ERROR);
        }

        //반환용 리스트
        List<FileMetadataResponse> fileResponseList = new ArrayList<>();

        //파일 리스트 업로드
        for(MultipartFile file : files){
            FileMetadata uploadedFileMetadata = fileHandler.uploadFile(file, FileCategory.POST_ATTACHMENT, postId);

            //이력 등록
            postFileHistoryService.registerPostFileHistory(uploadedFileMetadata, uploaderId);

            fileResponseList.add(FileMetadataResponse.toResponse(uploadedFileMetadata));
        }

        postEntity.setProjectLastActivityTimeNow();

        return fileResponseList;
    }

    /**
     * 2. 게시물 파일 조회
     * 함수명 : getPostFiles
     * @param postId 해당 게시물 id
     * @return List<fileMetadataDto> 해당 게시물의 파일 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileMetadataResponse> getPostFiles(Long postId){
        //게시물 유효성 검사
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //카테고리와 게시물 id로 찾은 모든 파일을 가져온다.
        //페이지네이션 없음
        List<FileMetadata> fileEntities = fileHandler.getFiles(FileCategory.POST_ATTACHMENT, postEntity.getId());

        // 파일이 없으면 빈 리스트 반환, 있으면 DTO로 변환하여 반환
        return Optional.ofNullable(fileEntities)
                .orElse(List.of()) // fileEntities가 null이면 빈 리스트 반환
                .stream()
                .map(FileMetadataResponse::toDto) // Entity -> Optional<DTO> 변환
                .flatMap(Optional::stream) // Optional을 풀어서 DTO만 리스트에 포함
                .collect(Collectors.toList());
    }

    /**
     * 3-1. 게시물 파일 단일 삭제(게시물 수정시)
     * 함수명 : deletePostFile
     * @param
     */
    @Override
    @Transactional
    public void deletePostFile(Long postId, Long fileId, Long deleterId, String deleterRole){
        //1. 게시물 유효성 검사
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //2. 삭제 수행자 권한 판별
        //계정 본인이 아니거나 admin이 아닌경우 예외발생
        if(!(postEntity.getCreatedBy().equals(deleterId) || "ADMIN".equals(deleterRole))){
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        //3. 해당 게시물 파일 목록에 해당 파일이 존재하는지 확인
        List<FileMetadata> fileEntities = fileHandler.getFiles(FileCategory.POST_ATTACHMENT, postEntity.getId());
        List<Long> fileIds = fileEntities.stream().map(FileMetadata::getId).collect(Collectors.toList());

        if(!fileIds.contains(fileId)){
            throw new BusinessException(ErrorCode.FILE_NOT_FOUND_ERROR);
        }

        //4. 해당 파일을 삭제한다.
        FileMetadata deletedFileMetadata = fileHandler.deleteFileById(fileId);

        //5. 삭제 이력 등록
        postFileHistoryService.deletePostFileHistory(deletedFileMetadata, deleterId);

        //6. 프로젝트 최종 활동 일자 변경
        postEntity.setProjectLastActivityTimeNow();
    }

    /**
     * 3-2. 게시물 파일 일괄 삭제(게시물 삭제시)
     * 함수명 : deleteAllPostFiles
     * @auth admin, 해당 게시물 작성자
     * @param postId 해당 게시물 id
     */
    @Override
    @Transactional
    public void deleteAllPostFiles(Long postId, Long deleterId, String deleterRole){
        //1. 게시물 유효성 검사
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //2. 수행자 권한 판별
        //2. 삭제 수행자 권한 판별
        //계정 본인이 아니거나 admin이 아닌경우 예외발생
        if(!(postEntity.getCreatedBy().equals(deleterId) || "ADMIN".equals(deleterRole))){
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        //3. 해당 게시물의 파일들을 전체 삭제한다.
        for(FileMetadata fileEntity : fileHandler.getFiles(FileCategory.POST_ATTACHMENT, postEntity.getId())) {
            FileMetadata deletedFileMetadata = fileHandler.deleteFileById(fileEntity.getId());

            //4. 이력 등록
            postFileHistoryService.deletePostFileHistory(deletedFileMetadata, deleterId);
        }
    }

}
