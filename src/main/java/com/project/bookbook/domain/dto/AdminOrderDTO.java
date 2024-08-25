package com.project.bookbook.domain.dto;

import java.time.LocalDateTime;

import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.entity.CouponEntity;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.entity.UserOrdersEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminOrderDTO {

	private long merchantUid; // 주문번호 fk
	private long userId; // 사용자ID fk
	private String userName;
	private long bookNum; //도서번호
	private int orderStatus; //주문상태
	//0:주문대기, 1:주문중, 2:배송중, 3:배송완료, 4:환불, 5:교환, 6:취소
	private long orderDetailNum; //주문상세번호
	private long orderCnt; //주문수량 
	private LocalDateTime orderDate; //주문날짜
	private long paidAmount; //총 결제금액
	private long couponNum; // 쿠폰 fk
	private String couponName;
	
	
	
	
	
	
}
