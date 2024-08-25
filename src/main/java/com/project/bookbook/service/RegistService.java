package com.project.bookbook.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookbook.domain.dto.RequestRegistDTO;

public interface RegistService {

	void saveProcess(RequestRegistDTO requestRegistDTO);

	Map<String, String> uploadFileToS3(MultipartFile bookImg) throws IOException;

}
