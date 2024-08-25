package com.project.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.OrderUpdateDTO;
import com.project.bookbook.domain.dto.SellerOrderDTO;
import com.project.bookbook.mapper.MypageUserMapper;
import com.project.bookbook.mapper.SellerMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.SellerOrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerOrderServiceProcess implements SellerOrderService{
	
	private final SellerMapper sellerMapper;
	private final MypageUserMapper userMapper;
	
	@Override
	public void findBook(Model model, CustomUserDetails seller) {
		long userId = seller.getUserId();
		String sellerName = userMapper.getSellerName(userId);
		List<SellerOrderDTO> list = sellerMapper.findOrder(sellerName);
		model.addAttribute("list",list);
		
	}

	@Override
	@Transactional
	public void updateProcess(long merchantUid) {
		sellerMapper.updateOrder(merchantUid);
		
	}

	
}
