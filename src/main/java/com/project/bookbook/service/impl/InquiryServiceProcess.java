package com.project.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.InquiryCreateDTO;
import com.project.bookbook.domain.dto.InquiryDTO;
import com.project.bookbook.domain.dto.InquiryDetailDTO;
import com.project.bookbook.mapper.InquiryMapper;
import com.project.bookbook.service.InquiryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryServiceProcess implements InquiryService{
	
	private final InquiryMapper inquiryMapper;
	
	@Override
	public void findAllProcess(Model model) {
		List<InquiryDTO> list = inquiryMapper.findAll();
		model.addAttribute("list",list);
		
	}

	@Override
	public void findAll(Model model, long qnaNum) {
		InquiryDTO qna = inquiryMapper.findInquiry(qnaNum);
		InquiryDetailDTO detail = inquiryMapper.findDetail(qnaNum);
		model.addAttribute("qna",qna);
		model.addAttribute("detail",detail);
		
	}

	@Override
	public void saveProcess(InquiryCreateDTO dto) {
		inquiryMapper.save(dto);
		
	}
	
}
