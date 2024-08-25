package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.accountUpdateDTO;
import com.project.bookbook.security.CustomUserDetails;

public interface MypageUserService {

	void readProcess(Model model, CustomUserDetails user);

	void updateProcess(accountUpdateDTO dto);

	void changeStatus(CustomUserDetails user);

}
