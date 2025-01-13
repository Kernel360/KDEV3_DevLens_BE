package global.util.file.Service;

import com.seveneleven.entity.file.FileMetadata;
import com.seveneleven.entity.file.constant.FileCategory;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.APIResponse;
import com.seveneleven.response.ErrorCode;
import com.seveneleven.util.file.Service.FileService;
import com.seveneleven.util.file.Service.S3ClientService;
import com.seveneleven.util.file.repository.FileMetadataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private S3ClientService s3ClientService;

    @Mock
    private FileMetadataRepository fileMetadataRepository;

    @Mock
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("파일 업로드 성공 테스트")
    void testUploadFile_Success() throws Exception {
        // Given
        String originalFilename = "testFile.jpg";
        String uniqueFileName = "unique-testFile.jpg";
        String s3Key = "1/USER_PROFILE_IMAGE/unique-testFile.jpg";
        String filePath = "https://s3.aws.com/bucket/path";

        when(mockFile.getOriginalFilename()).thenReturn(originalFilename);
        when(mockFile.getContentType()).thenReturn("image/jpeg");
        when(mockFile.getSize()).thenReturn(1024L);

        when(s3ClientService.generateUniqueFileName(originalFilename)).thenReturn(uniqueFileName);
        when(s3ClientService.generateS3Key(anyString(), anyLong(), eq(uniqueFileName))).thenReturn(s3Key);
        when(s3ClientService.uploadFile(mockFile, s3Key)).thenReturn(filePath);

        FileMetadata mockFileMetadata = FileMetadata.registerFile(
                FileCategory.USER_PROFILE_IMAGE,
                1L,
                originalFilename,
                uniqueFileName,
                "image/jpeg",
                ".jpg",
                1.0,
                filePath
        );

        when(fileMetadataRepository.save(any(FileMetadata.class))).thenReturn(mockFileMetadata);

        // When
        APIResponse response = fileService.uploadFile(mockFile, FileCategory.USER_PROFILE_IMAGE.name(), 1L);

        // Then
        assertNotNull(response);
        verify(fileMetadataRepository, times(1)).save(any(FileMetadata.class));
        verify(s3ClientService, times(1)).uploadFile(mockFile, s3Key);
    }

    @Test
    @DisplayName("파일 업로드 실패 테스트")
    void testUploadFile_S3UploadFailure() throws Exception {
        // Given
        String originalFilename = "testFile.jpg";
        String uniqueFileName = "unique-testFile.jpg";
        String s3Key = "1/USER_PROFILE_IMAGE/unique-testFile.jpg";

        when(mockFile.getOriginalFilename()).thenReturn(originalFilename);
        when(mockFile.getContentType()).thenReturn("image/jpeg");
        when(mockFile.getSize()).thenReturn(1024L);

        when(s3ClientService.generateUniqueFileName(originalFilename)).thenReturn(uniqueFileName);
        when(s3ClientService.generateS3Key(anyString(), anyLong(), eq(uniqueFileName))).thenReturn(s3Key);
        when(s3ClientService.uploadFile(mockFile, s3Key)).thenThrow(new RuntimeException("S3 upload failed"));

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            fileService.uploadFile(mockFile,  FileCategory.USER_PROFILE_IMAGE.name(), 1L);
        });

        assertEquals(ErrorCode.FILE_UPLOAD_FAIL_ERROR, exception.getErrorCode());
        verify(s3ClientService, times(1)).deleteFile(s3Key);
    }

    @Test
    @DisplayName("파일 카테고리 유효성 검사 테스트")
    void testUploadFile_InvalidFileCategory() {
        // Given
        when(mockFile.getOriginalFilename()).thenReturn("testFile.jpg");

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            fileService.uploadFile(mockFile, "INVALID_CATEGORY", 1L);
        });

        assertEquals(ErrorCode.INVALID_FILE_CATEGORY_ERROR, exception.getErrorCode());
        verifyNoInteractions(s3ClientService, fileMetadataRepository);
    }

    @Test
    @DisplayName("파일 확장자 유효성 검사 테스트")
    void testUploadFile_InvalidFileExtension() {
        // Given
        String originalFilename = "testFile.exe";
        when(mockFile.getOriginalFilename()).thenReturn(originalFilename);
        when(mockFile.getContentType()).thenReturn("application/octet-stream");

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            fileService.uploadFile(mockFile,  FileCategory.USER_PROFILE_IMAGE.name(), 1L);
        });

        assertEquals(ErrorCode.FORMAT_NOT_PERMITTED_ERROR, exception.getErrorCode());
        verifyNoInteractions(s3ClientService, fileMetadataRepository);
    }

    @Test
    @DisplayName("파일 사이즈 유효성 검사 테스트")
    void testUploadFile_FileSizeExceed() {
        // Given
        when(mockFile.getOriginalFilename()).thenReturn("testFile.jpg");
        when(mockFile.getContentType()).thenReturn("image/jpeg");
        when(mockFile.getSize()).thenReturn(25L * 1024 * 1024); // 25MB

        // When & Then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            fileService.uploadFile(mockFile, FileCategory.USER_PROFILE_IMAGE.name(), 1L);
        });

        assertEquals(ErrorCode.FILE_SIZE_EXCEED_ERROR, exception.getErrorCode());
        verifyNoInteractions(s3ClientService, fileMetadataRepository);
    }
}