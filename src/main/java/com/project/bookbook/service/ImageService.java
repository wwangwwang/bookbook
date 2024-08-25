package com.project.bookbook.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.project.bookbook.domain.entity.ImageEntity;

public interface ImageService {
	ImageEntity uploadImage(MultipartFile file) throws IOException;
    ImageEntity getImageById(Long id);
    void deleteImage(Long id);
}