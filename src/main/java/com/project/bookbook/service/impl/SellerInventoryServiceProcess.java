package com.project.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.SellerInventoryDTO;
import com.project.bookbook.mapper.MypageUserMapper;
import com.project.bookbook.mapper.SellerMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.SellerInventoryService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class SellerInventoryServiceProcess implements SellerInventoryService{
	
	private final SellerMapper sellerMapper;
	private final MypageUserMapper userMapper;
	
	@Override
	public void findBook(Model model, CustomUserDetails seller) {
		long userId = seller.getUserId();
		String sellerName = userMapper.getSellerName(userId);
		List<SellerInventoryDTO> list = sellerMapper.findAll(sellerName);
		model.addAttribute("list",list);
		
	}

	@Override
	public void deleteProcess(long bookNum) {
		sellerMapper.deleteBook(bookNum);
		
	}

	
}
