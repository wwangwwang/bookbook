package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.CouponListDTO;
import com.project.bookbook.domain.entity.UserCouponEntity;
import com.project.bookbook.security.CustomUserDetails;

public interface UserCouponService {

	void list(Model model);

	CouponListDTO getCouponById(String id);

	UserCouponEntity assignCouponToUser(String couponId, long userId);


}
