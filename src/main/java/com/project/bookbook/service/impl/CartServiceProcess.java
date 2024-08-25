package com.project.bookbook.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.entity.CartDetailEntity;
import com.project.bookbook.domain.entity.CartEntity;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.CartDetailRepository;
import com.project.bookbook.domain.repository.CartRepository;
import com.project.bookbook.domain.repository.UserRepository;
import com.project.bookbook.service.CartService;

import jakarta.transaction.Transactional;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.CartDetailDTO;
import com.project.bookbook.domain.dto.UpdateCartDTO;
import com.project.bookbook.mapper.CartMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.CartService;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceProcess implements CartService{


	private final CartMapper cartMapper;
	
	@Override
	public void findAllProcess(Model model, CustomUserDetails user) {
		long userId = user.getUserId();
		List<CartDetailDTO> cartDetails = cartMapper.findAllCartDetail(userId);
		model.addAttribute("cartDetails", cartDetails);
		
	}

	@Override
	public void deleteCartDetail(long cartDetailNum) {
		cartMapper.deleteCartDetail(cartDetailNum);
		
	}

	@Override
	public void cartEmptyProcess(CustomUserDetails user) {
		long userId = user.getUserId();
		long cartNum = cartMapper.findCartNum(userId);
		cartMapper.deleteAllCart(cartNum);
	}

	@Override
	@Transactional
	public void updateCartItemQuantity(UpdateCartDTO dto) {
		cartMapper.updateCartItemQuantity(dto);
		
	}


}