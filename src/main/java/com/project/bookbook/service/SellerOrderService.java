package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.OrderUpdateDTO;
import com.project.bookbook.security.CustomUserDetails;

public interface SellerOrderService {

	void findBook(Model model, CustomUserDetails seller);

	void updateProcess(long merchantUid);

}
