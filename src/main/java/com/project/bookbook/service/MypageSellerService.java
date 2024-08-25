package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.MypageSellerDTO;
import com.project.bookbook.domain.dto.SellerUpdateDTO;
import com.project.bookbook.security.CustomUserDetails;

public interface MypageSellerService {
	
	void readProcess(Model model, CustomUserDetails seller);

	void updateProcess(SellerUpdateDTO dto);

}
