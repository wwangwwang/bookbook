package com.project.bookbook.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_orders")
public class UserOrdersEntity{

	@Id
	private long merchantUid; //주문번호
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "userId", nullable = false)
	private UserEntity user; // 사용자ID fk
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "couponNum", nullable = true)
	private CouponEntity coupon; // 쿠폰 fk
	
	@CreationTimestamp
	@Column(columnDefinition = "timestamp")
	private LocalDateTime orderDate; //주문날짜
	
	@Column(nullable = true)
	private long paidAmount; //총 결제금액
	
	private String cardName; //카드사 이름
	
	@Column(name = "card_number", nullable = true)
	private int cardNumber; //결제 카드번호
	
	@Column(nullable=true)
	@ColumnDefault("0")
	private String orderStatus; //주문상태
	//0:주문대기, 1:주문완료, 2:배송중, 3:배송완료, 4:환불, 5:교환, 6:취소
	
	private String field; //환불사유
	
	private String exchangeReason; //교환사유
	
}

