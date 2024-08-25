package com.project.bookbook.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookbook.domain.entity.ImageEntity;
import com.project.bookbook.domain.repository.ImageEntityRepository;
import com.project.bookbook.service.ImageService;
import com.project.bookbook.utils.FileUploadUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceProcess implements ImageService {
	
	private final ImageEntityRepository imageRepository;
    private final FileUploadUtils fileUploadUtil;
    
	
	
    // 이미지 업로드 메서드
	@Override
	@Transactional
	public ImageEntity uploadImage(MultipartFile file) throws IOException {
		  // S3에 파일 업로드 후 결과를 받아옵니다.
		  Map<String, String> uploadResult = fileUploadUtil.uploadFileToS3(file);
	        
		  	// 업로드 결과를 바탕으로 ImageEntity 객체를 생성합니다.	        
	        ImageEntity image = new ImageEntity();
	        image.setFileName(uploadResult.get("fileName"));
	        // 여기서 S3 URL이 설정됩니다.
	        image.setFileUrl(uploadResult.get("url"));
	        image.setFileType(uploadResult.get("fileType"));
	        image.setFileSize(Long.parseLong(uploadResult.get("fileSize")));
	        
	        // 생성된 ImageEntity를 데이터베이스에 저장하고 반환합니다.
	        // 여기서 DB에 저장됩니다.
	        return imageRepository.save(image);
	    }

    // ID로 이미지를 조회하는 메서드
	
	@Override
	public ImageEntity getImageById(Long id) {
		 return imageRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Image not found"));
	    }

	@Override
	@Transactional
	public void deleteImage(Long id) {
		ImageEntity image = getImageById(id);
        imageRepository.delete(image);
    }
}