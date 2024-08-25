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
import com.project.bookbook.service.CartItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceProcess implements CartItemService{
	
	@Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private UserRepository userRepository;

	@Override
	public void addToCart(BookEntity book, Long userId, int quantity) {
		UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	        System.out.println("///////////사용자 ID: " + userId);

	        CartEntity cart = cartRepository.findByUser_UserId(userId)
	            .orElseGet(() -> {
	                CartEntity newCart = new CartEntity();
	                newCart.setUser(user);
	                return cartRepository.save(newCart);
	            });
	        System.out.println("///////////장바구니: " + cart);

	        CartDetailEntity cartDetail = cartDetailRepository
	            .findByCart_CartNumAndBook_BookNum(cart.getCartNum(), book.getBookNum())
	            .orElseGet(() -> {
	                CartDetailEntity newCartDetail = new CartDetailEntity();
	                newCartDetail.setCart(cart);
	                newCartDetail.setBook(book);
	                newCartDetail.setCartCnt(0L);
	                return newCartDetail;
	            });

	        cartDetail.setCartCnt(cartDetail.getCartCnt() + quantity);
	        cartDetailRepository.save(cartDetail);
	        System.out.println("///////////장바구니 디테일 (수량 추가 후): " + cartDetail + ", 추가된 수량: " + quantity);
	    }

	}
	
