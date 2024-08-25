package com.project.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.QNAAnswerDTO;
import com.project.bookbook.domain.dto.QNACreateDTO;
import com.project.bookbook.domain.dto.QNADTO;
import com.project.bookbook.mapper.QNAMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.QNAService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QNAServiceProcess implements QNAService{
	
	private final QNAMapper qnaMapper;

	@Override
	public void findAllProcess(Model model, CustomUserDetails user) {
		long userId = user.getUserId();
		List<QNADTO> list = qnaMapper.findAll(userId);
		model.addAttribute("list", list);
		
	}

	@Override
	public void findProcess(Model model, long qnaNum) {
		QNADTO qna = qnaMapper.findQna(qnaNum);
		QNAAnswerDTO answer = qnaMapper.findAnswer(qnaNum);
		model.addAttribute("qna", qna);
		model.addAttribute("answer", answer);
	}
	
	@Override
	public void saveProcess(QNACreateDTO dto) {
		qnaMapper.save(dto);
	}

	@Override
	public void deleteProcess(long qnaNum) {
		qnaMapper.deleteAnswer(qnaNum);
		qnaMapper.deleteQna(qnaNum);
	}


}
