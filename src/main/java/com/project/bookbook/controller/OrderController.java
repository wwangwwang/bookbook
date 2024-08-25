package com.project.bookbook.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.CouponService;
import com.project.bookbook.service.MypageUserService;
import com.project.bookbook.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	private final MypageUserService userService;
	
	@GetMapping("/mypage/orders")
	public String order(@AuthenticationPrincipal CustomUserDetails user, Model model) {
		orderService.findUserOrderProcess(user, model);
		return "views/mypage/order";
	}
	
	//주문 상세
	@GetMapping("/mypage/orders/detail/{merchantUid}")
	public String orderDetail(@PathVariable("merchantUid") long merchantUid, Model model, @AuthenticationPrincipal CustomUserDetails user) {
		userService.readProcess(model, user);
		orderService.findOrdersInfo(model, merchantUid);
		orderService.findByMerchantUidAndCoupon(merchantUid, model);
		return "views/mypage/order-detail";
	}
	
	//교환,환불 목록
	@GetMapping("/mypage/orders/exchange")
	public String exchange(@AuthenticationPrincipal CustomUserDetails user, Model model) {
		orderService.findUserOrderProcess(user, model);
		return "views/mypage/order-exchanged";
	}
	
	//취소 목록
	@GetMapping("/mypage/orders/cancel")
	public String cancel(@AuthenticationPrincipal CustomUserDetails user, Model model) {
		orderService.findUserOrderProcess(user, model);
		return"views/mypage/order-canceled";
	}
	
	
}
