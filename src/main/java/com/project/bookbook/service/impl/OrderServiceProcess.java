package com.project.bookbook.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.CartBookCntDTO;
import com.project.bookbook.domain.dto.OrderDetailDTO;
import com.project.bookbook.domain.dto.OrdersDetailDTO;
import com.project.bookbook.domain.dto.PaymentPostDTO;
import com.project.bookbook.domain.dto.UserOrderDTO;
import com.project.bookbook.mapper.CartMapper;
import com.project.bookbook.mapper.OrdersMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.CouponService;
import com.project.bookbook.service.OrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceProcess implements OrderService{
	
	private final CartMapper cartMapper;
    private final OrdersMapper ordersMapper;
    private final CouponService couponService;
	
	@Override
	@Transactional
	public long createOrder(List<Long> cartDetailNums, CustomUserDetails user) {
		
		//1. 새로운 주문 생성
		final long merchantUid = generateOrderNumber(); //주문번호 PK 생성
		Map<String, Long> params = new HashMap<>();
		params.put("userId", user.getUserId());
		params.put("merchantUid", merchantUid);
		ordersMapper.insertUserOrder(params);
		
		//2. cartDetail에서 데이터를 읽어와 도서번호, 상품수량 orderDetail에 복사
		List<CartBookCntDTO> cartDetails = cartMapper.selectCartDetailsByIds(cartDetailNums);
        for (CartBookCntDTO cartDetail : cartDetails) {
        	
        	Map<String, Object> cartDetailParams = new HashMap<>();
        	cartDetailParams.put("merchantUid", merchantUid);
        	cartDetailParams.put("bookNum", cartDetail.getBookNum());
        	cartDetailParams.put("cartCnt", cartDetail.getCartCnt());
        	
            ordersMapper.insertUserOrdersDetail(cartDetailParams);
        }
		
        //3. 주문번호 반환
		return merchantUid;
	}

	@Override
	public void findOrdersInfo(Model model, long merchantUid) {
		List<OrderDetailDTO> orderBooks = ordersMapper.findByOrderBook(merchantUid);
		model.addAttribute("orderBooks", orderBooks);
		model.addAttribute("merchantUid", merchantUid);
	}
	
	//주문번호 생성
	private long generateOrderNumber() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateTimePart = LocalDateTime.now().format(dtf);
        Random random = new Random();
        int randomPart = 100000 + random.nextInt(900000); // 100000 ~ 999999
        return Long.parseLong(dateTimePart + randomPart);
    }
	
	@Override
	@Transactional
	public void orderCompletion(PaymentPostDTO dto) {
		if(dto.getCouponNum() != 0) {
			ordersMapper.orderCompletion(dto);
		}else {
			ordersMapper.orderCompletionNoCoupon(dto);
		}
		
	}

	@Override
	public void findByMerchantUid(long merchantUid, Model model) {
		model.addAttribute("merchantUid", merchantUid);
		
		OrdersDetailDTO ordersDetail = ordersMapper.findByMerchantUid(merchantUid);
		model.addAttribute("ordersDetail", ordersDetail);
		
	}

	@Override
	public void findByMerchantUidAndCoupon(long merchantUid, Model model) {
		model.addAttribute("merchantUid", merchantUid);
		
		OrdersDetailDTO ordersDetail = ordersMapper.findByMerchantUid(merchantUid);
		model.addAttribute("ordersDetail", ordersDetail);
		
		if(ordersDetail.getCouponNum() != 0) {
			int couponRate = couponService.findByCouponNum(ordersDetail.getCouponNum());
			model.addAttribute("couponRate", couponRate);
		}
		
	}

	@Override
	public void findUserOrderProcess(CustomUserDetails user, Model model) {
		long userId = user.getUserId();
		List<UserOrderDTO> userOrders = ordersMapper.findByUserId(userId);
		
		for(int i=0; i<userOrders.size(); i++) {
			long merchantUid = userOrders.get(i).getMerchantUid();
			userOrders.get(i).setBookImg(ordersMapper.findByOrderBookOne(merchantUid).getBookImg());
			userOrders.get(i).setBookName(ordersMapper.findByOrderBookOne(merchantUid).getBookName());
		}
		model.addAttribute("userOrders", userOrders);
		
		List<UserOrderDTO> orderCompleted = userOrders.stream().filter(order -> order.getOrderStatus() == 1).toList();
		List<UserOrderDTO> inProgress = userOrders.stream().filter(order -> order.getOrderStatus() == 2).toList();
		List<UserOrderDTO> shipmentCompleted = userOrders.stream().filter(order -> order.getOrderStatus() == 3).toList();
		List<UserOrderDTO> canceled = userOrders.stream().filter(order -> order.getOrderStatus() == 4).toList();

        model.addAttribute("orderCompleted", orderCompleted);
        model.addAttribute("inProgress", inProgress);
        model.addAttribute("shipmentCompleted", shipmentCompleted);
        model.addAttribute("canceled", canceled);
	}
	
}