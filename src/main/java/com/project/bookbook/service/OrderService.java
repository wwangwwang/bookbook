package com.project.bookbook.service;

import java.util.List;

import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.PaymentPostDTO;
import com.project.bookbook.security.CustomUserDetails;

public interface OrderService {

	long createOrder(List<Long> cartDetailNums, CustomUserDetails user);

	void findOrdersInfo(Model model, long merchantUid);

	void orderCompletion(PaymentPostDTO dto);

	void findByMerchantUid(long merchantUid, Model model);

	void findByMerchantUidAndCoupon(long merchantUid, Model model);

	void findUserOrderProcess(CustomUserDetails user, Model model);


}
