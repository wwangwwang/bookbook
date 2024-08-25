package com.project.bookbook.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class FileUploadUtil {
	//S3의 정보가 있어야함
	private final S3Client s3Client;
	
	@Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    
    @Value("${spring.cloud.aws.s3.upload-src}")
    private String upload;
    
    @Value("${spring.cloud.aws.s3.upload-temp}")
    private String temp;
    
    public Map<String, String> uploadFileToS3(MultipartFile file) throws IOException {
        String orgFileName = file.getOriginalFilename();
        String newFileName = newFileName(orgFileName);
        String tempKey = temp + newFileName;
        
        InputStream is = file.getInputStream();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(tempKey)
                .contentType(file.getContentType())
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        RequestBody requestBody = RequestBody.fromInputStream(is, file.getSize());
        
        // S3에 파일 업로드 처리
        s3Client.putObject(putObjectRequest, requestBody);
        
        String url = s3Client.utilities()
                .getUrl(GetUrlRequest.builder()
                    .bucket(bucket)
                    .key(tempKey)
                    .build())
                .toString();
        
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("url", url);
        resultMap.put("tempKey", tempKey);
        resultMap.put("orgName", orgFileName);
        
        return resultMap;
    }

    
    private String newFileName(String orgFileName) {
        int index = orgFileName.lastIndexOf(".");
        return UUID.randomUUID().toString() + orgFileName.substring(index);
    }
    
    public String ImagesToS3(String tempKey) {
        if (tempKey == null) {
            throw new IllegalArgumentException("tempKey cannot be null");
        }
        
        String[] str = tempKey.split("/");
        String destinationkey = upload + str[str.length - 1];
        
        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .sourceBucket(bucket)
                .sourceKey(tempKey)
                .destinationBucket(bucket)
                .destinationKey(destinationkey)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        
        s3Client.copyObject(copyObjectRequest);
        
        s3Client.deleteObject(builder -> builder.bucket(bucket).key(tempKey)); // 수정된 부분
        
        String url = s3Client.utilities()
                .getUrl(builder -> builder.bucket(bucket).key(destinationkey))
                .toString();
        
        return url;
    }

}