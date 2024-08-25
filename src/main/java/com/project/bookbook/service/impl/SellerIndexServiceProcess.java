package com.project.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.SellerIndexDTO;
import com.project.bookbook.mapper.SellerMapper;
import com.project.bookbook.service.SellerIndexService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerIndexServiceProcess implements SellerIndexService{
	
	private final SellerMapper sellerMapper;
	
	@Override
	public void find(Model model) {
		List<SellerIndexDTO> list = sellerMapper.find();
		model.addAttribute("list",list);
		
	}

}
