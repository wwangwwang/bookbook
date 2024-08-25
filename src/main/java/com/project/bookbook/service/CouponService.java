package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.security.CustomUserDetails;

public interface CouponService {

	void findProcess(Model model, CustomUserDetails user);

	boolean checkProcess(long couponNum);

	void addProcess(long couponNum, CustomUserDetails user);

	boolean checkDuplicateCoupon(long couponNum, CustomUserDetails user);

	int findByCouponNum(long couponNum);

	void couponStatusChange(long couponNum, CustomUserDetails user);

	void deleteUserCoupon(long couponNum, CustomUserDetails user);

}
