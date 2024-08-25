package com.project.bookbook.domain.dto;

import com.project.bookbook.domain.entity.CouponEntity;
import com.project.bookbook.domain.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponDTO {
	
	private UserEntity user;
	private CouponEntity coupon ;

}
