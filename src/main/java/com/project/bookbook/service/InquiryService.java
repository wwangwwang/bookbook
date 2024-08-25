package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.InquiryCreateDTO;

public interface InquiryService {

	void findAllProcess(Model model);

	void findAll(Model model, long qnaNum);

	void saveProcess(InquiryCreateDTO dto);

}
