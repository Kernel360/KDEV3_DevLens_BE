package com.seveneleven.board.service;

import com.seveneleven.board.repository.PostRepository;
import com.seveneleven.entity.board.Post;
import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.Service.FileService;
import com.seveneleven.util.file.dto.FileMetadataDto;
import com.seveneleven.util.file.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostFileService {
    private final FileService fileService;
    private final PostRepository postRepository;
    private final FileMetadataRepository fileMetadataRepository;

    private static final int MAX_FILE_COUNT = 10; //게시물별 최대 파일 수(10개)

    /**
     * 1. 게시물 파일 업로드
     * 함수명 : uploadPostFiles
     * @auth admin, 게시물 작성자
     * @param files 업로드할 로고 이미지 파일들
     * @param postId 해당 게시물 id
     * @param uploaderId 업로드 수행자 id
     */
    @Transactional
    public void uploadPostFiles(List<MultipartFile> files, Long postId, Long uploaderId){
        //1. 게시물 id로 존재여부 판별
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //TODO) 2. 수행자 권한 판별 - admin, 해당 게시글 작성자

        //3. 저장할 파일 갯수 + 현재 저장 갯수 >= 11인지 판별
        //현재 저장된 파일 갯수 확인
        Integer currentFileCnt = fileMetadataRepository.countByCategoryAndReferenceId(FileCategory.POST_ATTACHMENT, postEntity.getId());
        if(currentFileCnt + files.size() > MAX_FILE_COUNT){
            throw new BusinessException(ErrorCode.FILE_QUANTITY_EXCEED_ERROR);
        }

        //파일 리스트 업로드
        for(MultipartFile file : files){
            fileService.uploadFile(file, FileCategory.POST_ATTACHMENT, postId);
        }

        //TODO) 4. 파일 업로드 이력 추가
    }

    /**
     * 2. 게시물 파일 조회
     * 함수명 : getPostFiles
     * @param postId 해당 게시물 id
     * @return List<fileMetadataDto> 해당 게시물의 파일 리스트
     */
    @Transactional(readOnly = true)
    public List<FileMetadataDto> getPostFiles(Long postId){
        //게시물 유효성 검사
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //카테고리와 게시물 id로 찾은 모든 파일을 가져온다.
        //페이지네이션 없음
        List<FileMetadata> fileEntities = fileService.getFiles(FileCategory.POST_ATTACHMENT, postEntity.getId());

        //entity를 dto에 담는다.
        List<FileMetadataDto> fileMetadataDtos = new ArrayList<>();
        for (FileMetadata fileMetadata : fileEntities) {
            FileMetadataDto dto = FileMetadataDto.toDto(fileMetadata);
            fileMetadataDtos.add(dto);
        }

        //반환
        return fileMetadataDtos;
    }

    /**
     * 3-1. 게시물 파일 단일 삭제(수정시)
     * 함수명 : deletePostFile
     * @param
     */
    @Transactional
    public void deletePostFile(Long postId, Long fileId, Long deleterId){
        //1. 게시물 유효성 검사
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //TODO) 2. 삭제 수행자 권한 판별

        //3. 해당 게시물 파일 목록에 해당 파일이 존재하는지 확인
        List<FileMetadata> fileEntities = fileService.getFiles(FileCategory.POST_ATTACHMENT, postEntity.getId());
        List<Long> fileIds = fileEntities.stream().map(FileMetadata::getId).collect(Collectors.toList());

        if(!fileIds.contains(fileId)){
            throw new BusinessException(ErrorCode.FILE_NOT_FOUND_ERROR);
        }

        //4. 해당 파일을 삭제한다.
        fileService.deleteFileById(fileId);
    }

    /**
     * 3-2. 게시물 파일 일괄 삭제(삭제시)
     * 함수명 : deleteAllPostFiles
     * @auth admin, 해당 게시물 작성자
     * @param postId 해당 게시물 id
     */
    @Transactional
    public void deleteAllPostFiles(Long postId, Long deleterId){
        //1. 게시물 유효성 검사
        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));

        //TODO) 2. 수행자 권한 판별

        //3. 해당 게시물의 파일들을 전체 삭제한다.
        for(FileMetadata fileEntity : fileService.getFiles(FileCategory.POST_ATTACHMENT, postEntity.getId())) {
            fileService.deleteFileById(fileEntity.getId());
        }
    }

}
