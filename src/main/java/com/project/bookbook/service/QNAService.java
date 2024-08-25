package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.QNACreateDTO;
import com.project.bookbook.security.CustomUserDetails;

public interface QNAService {

	void findAllProcess(Model model, CustomUserDetails user);

	void saveProcess(QNACreateDTO dto);

	void findProcess(Model model, long qnaNum);

	void deleteProcess(long qnaNum);

}
