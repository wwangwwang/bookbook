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
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class FileUploadUtils {
	//S3의 정보가 있어야함
		private final S3Client s3Client;
		
		 @Value("${spring.cloud.aws.s3.bucket}")
		    private String bucket;
		    
		    @Value("${spring.cloud.aws.s3.upload-src}")
		    private String upload;
		    
		    public Map<String, String> uploadFileToS3(MultipartFile file) throws IOException {
		        String orgFileName = file.getOriginalFilename();
		        String newFileName = newFileName(orgFileName);
		        String bucketKey = upload + newFileName;
		        
		        InputStream is = file.getInputStream();
		        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
		                .bucket(bucket)
		                .key(bucketKey)
		                .contentType(file.getContentType())
		                .acl(ObjectCannedACL.PUBLIC_READ)
		                .build();
		        RequestBody requestBody = RequestBody.fromInputStream(is, file.getSize());
		        
		        // S3에 파일 업로드 처리
		        s3Client.putObject(putObjectRequest, requestBody);
		        
		        String url = s3Client.utilities()
		                .getUrl(builder -> builder.bucket(bucket).key(bucketKey))
		                .toString();
		        
		        Map<String, String> resultMap = new HashMap<>();
		        resultMap.put("url", url);
		        resultMap.put("fileName", newFileName);
		        resultMap.put("fileType", file.getContentType());
		        resultMap.put("fileSize", String.valueOf(file.getSize()));
		        
		        return resultMap;
		    }
		    
		    private String newFileName(String orgFileName) {
		        int index = orgFileName.lastIndexOf(".");
		        return UUID.randomUUID().toString() + orgFileName.substring(index);
		    }
		    
		    public List<String> ImagesToS3(List<String> tempKeys) {
				
				List<String> uploadKeys=new ArrayList<>();
				tempKeys.forEach(tempkey->{
					
					String[] str = tempkey.split("/");
					String destinationkey=upload+str[str.length-1];
					
					CopyObjectRequest copyObjectRequest=CopyObjectRequest.builder()
							.sourceBucket(bucket)
							.sourceBucket(tempkey)
							.destinationBucket(bucket)
							.destinationBucket(destinationkey)
							.build();
					
					s3Client.copyObject(copyObjectRequest);
					s3Client.deleteObject(builder->builder.bucket(bucket).bucket(tempkey));
					
					String url = s3Client.utilities()
							.getUrl(builder->builder.bucket(bucket).key(destinationkey))
							.toString().substring(6);
						
					uploadKeys.add(url);
				});
				
				return uploadKeys;
			}
		}