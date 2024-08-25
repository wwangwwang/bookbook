package com.project.bookbook.service;

import org.springframework.ui.Model;

import com.project.bookbook.security.CustomUserDetails;

public interface FavoriteService {

	void findByUser(Model model, CustomUserDetails user);

	void deleteProcess(long bookNum, CustomUserDetails user);

	

}
