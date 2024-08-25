package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.project.bookbook.domain.dto.QNAAnswerDTO;
import com.project.bookbook.domain.dto.QNACreateDTO;
import com.project.bookbook.domain.dto.QNADTO;
import com.project.bookbook.security.CustomUserDetails;

@Mapper
public interface QNAMapper {
	
	List<QNADTO> findAll(@Param("userId") long userId);

	void save(@Param("dto") QNACreateDTO dto);

	QNADTO findQna(@Param("qnaNum") long qnaNum);

	QNAAnswerDTO findAnswer(long qnaNum);

	void deleteAnswer(long qnaNum);

	void deleteQna(long qnaNum);

}
