package com.project.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.AdminIndexDTO;
import com.project.bookbook.domain.dto.AdminOrderDTO;
import com.project.bookbook.domain.dto.ReviewDTO;
import com.project.bookbook.mapper.AdminMapper;
import com.project.bookbook.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceProcess implements AdminService{
	
	private final AdminMapper adminMapper;
	
	@Override
	public void find(Model model) {
		List<AdminIndexDTO> list = adminMapper.find();
		List<ReviewDTO> reviews = adminMapper.findTop8Reviews();
        model.addAttribute("reviews", reviews);
		model.addAttribute("list",list);
		
	}

	@Override
	public void findReviews(Model model) {
		List<ReviewDTO> reviews = adminMapper.findAllReviews();
        model.addAttribute("reviews", reviews);
		
	}

	@Override
	public void deleteReview(Long reviewNum) {
		adminMapper.deleteReview(reviewNum);
		
	}

	@Override
	public void findOrder(Model model) {
		List<AdminOrderDTO> list = adminMapper.findOrder();
		model.addAttribute("list",list);
		
	}

	@Override
	public void findinquiryUpdate(long qnaNum) {
		adminMapper.findInquiryUpdate(qnaNum);
		
	}

}
