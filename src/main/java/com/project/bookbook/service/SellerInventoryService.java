package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.security.CustomUserDetails;

public interface SellerInventoryService {

	void findBook(Model model, CustomUserDetails seller);

	void deleteProcess(long bookNum);

}
