package com.project.bookbook.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.MypageSellerDTO;
import com.project.bookbook.domain.dto.SellerUpdateDTO;
import com.project.bookbook.mapper.MypageUserMapper;
import com.project.bookbook.mapper.SellerMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.MypageSellerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageSellerServiceProcess implements MypageSellerService {

	private final SellerMapper sellerMapper;
	private final MypageUserMapper userMapper;

	@Override
	public void readProcess(Model model, CustomUserDetails seller) {
		long userId = seller.getUserId();
		long sellerId = userMapper.getSellerId(userId);
		MypageSellerDTO sellerInfo = sellerMapper.findProcess(sellerId);
		model.addAttribute("seller", sellerInfo);

	}

	@Override
	public void updateProcess(SellerUpdateDTO dto) {
		sellerMapper.updateId(dto);

	}

}
