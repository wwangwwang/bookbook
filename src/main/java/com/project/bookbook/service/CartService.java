package com.project.bookbook.service;


import com.project.bookbook.domain.dto.UpdateCartDTO;
import com.project.bookbook.domain.entity.BookEntity;
import org.springframework.ui.Model;

import com.project.bookbook.security.CustomUserDetails;

public interface CartService {

	void findAllProcess(Model model, CustomUserDetails user);

	void deleteCartDetail(long cartDetailNum);

	void cartEmptyProcess(CustomUserDetails user);

	void updateCartItemQuantity(UpdateCartDTO dto);

	


}