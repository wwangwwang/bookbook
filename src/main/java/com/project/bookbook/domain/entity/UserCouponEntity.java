package com.project.bookbook.domain.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "user_coupon")
public class UserCouponEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userCouponNum; // 쿠폰 번호
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "userId", nullable = false)
	private UserEntity user; // 사용자ID fk
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "couponNum", nullable = false)
	private CouponEntity coupon ; // 쿠폰번호 fk
	
	@Column(nullable=true)
	@ColumnDefault("0")
	private int status; //0:사용전, 1:사용후

}
