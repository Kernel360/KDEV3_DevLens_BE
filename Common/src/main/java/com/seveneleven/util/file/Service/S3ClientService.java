package com.seveneleven.util.file.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.seveneleven.exception.BusinessException;
import com.seveneleven.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ClientService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 고유한 파일 이름 생성(UUID)
     * S3에 저장되는 파일은 고유 이름을 가져야 함.
     */
    public String generateUniqueFileName(String originalFileName) {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + fileExtension;
    }

    /**
     * S3 키 생성
     * 키 = S3 저장시 파일 경로 (파일카테고리/참조ID/uuid파일명)
     *
     */
    public String generateS3Key(String category, Long referenceId, String fileName) {
        return new StringBuilder()
                .append(category)
                .append("/")
                .append(referenceId)
                .append("/")
                .append(fileName)
                .toString();
    }

    /**
     * 파일 업로드
     */
    public String uploadFile(MultipartFile file, String s3Key) {
        try {
            //S3 저장할 파일의 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            //S3에 파일을 업로드하기 위한 객체 설정
            amazonS3.putObject(new PutObjectRequest(bucket, s3Key, file.getInputStream(), metadata));

            //업로드한 파일 URL 반환
            return amazonS3.getUrl(bucket, s3Key).toString();

        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), ErrorCode.S3_UPLOAD_FAIL_ERROR);
        }
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String s3Key) {
        amazonS3.deleteObject(bucket, s3Key);
    }

//    /**
//     * S3 파일 다운로드 (바이트 배열로 반환)
//     */
//    public byte[] downloadFile(String s3Key) {
//        try (S3Object s3Object = amazonS3.getObject(bucket, s3Key);
//             S3ObjectInputStream inputStream = (s3Object != null ? s3Object.getObjectContent() : null)) {
//
//            if (s3Object == null || inputStream == null) {
//                throw new RuntimeException("S3 OBJECT IS NULL : " + s3Key);
//            }
//
//            return inputStream.readAllBytes();
//        } catch (IOException e) {
//            throw new RuntimeException("FAILED TO DOWNLOAD FILE FROM S3", e);
//        }
//    }
}