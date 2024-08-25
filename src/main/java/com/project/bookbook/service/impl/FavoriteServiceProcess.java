package com.project.bookbook.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.FavoriteListDTO;
import com.project.bookbook.mapper.FavoriteMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.FavoriteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceProcess implements FavoriteService{
	
	private final FavoriteMapper favoriteMapper;
	
	@Override
	public void findByUser(Model model, CustomUserDetails user) {
		long userId = user.getUserId();
		List<FavoriteListDTO> favorites = favoriteMapper.findByUser(userId);
		model.addAttribute("favorites", favorites);
		
	}

	@Override
	public void deleteProcess(long bookNum, CustomUserDetails user) {
		Map<String, Long> params = new HashMap<>();
		params.put("userId", user.getUserId());
		params.put("bookNum", bookNum);
		favoriteMapper.deleteFavorite(params);
		
	}

}
