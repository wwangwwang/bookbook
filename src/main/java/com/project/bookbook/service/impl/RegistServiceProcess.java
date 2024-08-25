package com.project.bookbook.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookbook.domain.dto.RequestRegistDTO;
import com.project.bookbook.domain.repository.BookRepository;
import com.project.bookbook.service.RegistService;
import com.project.bookbook.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistServiceProcess implements RegistService{
	
	private final BookRepository bookRepository;
	private final FileUploadUtil fileUploadUtil;
	
	@Override
	public Map<String, String> uploadFileToS3(MultipartFile bookImg) throws IOException {
		return fileUploadUtil.uploadFileToS3(bookImg);
	}

	@Override
    public void saveProcess(RequestRegistDTO requestRegistDTO) {
		String uploadUrl=fileUploadUtil.ImagesToS3(requestRegistDTO.getTempKey());
		bookRepository.save(requestRegistDTO.toBookEntity(uploadUrl));
    }

}
