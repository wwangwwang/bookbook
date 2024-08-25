package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.project.bookbook.domain.dto.InquiryCreateDTO;
import com.project.bookbook.domain.dto.InquiryDTO;
import com.project.bookbook.domain.dto.InquiryDetailDTO;

@Mapper
public interface InquiryMapper {

	@Select("select * from qna order by qna_num desc")
	List<InquiryDTO> findAll();

	@Select("select * from qna where qna_num=#{qnaNum}")
	InquiryDTO findInquiry(@Param("qnaNum") long qnaNum);

	@Select("select * from qna_answer where qna_num=#{qnaNum}")
	InquiryDetailDTO findDetail(@Param("qnaNum") long qnaNum);

	@Insert("insert into qna_answer (title, content, qna_num, date) values(#{dto.title}, #{dto.content}, #{dto.qnaNum}, current_timestamp)")
	void save(@Param("dto") InquiryCreateDTO dto);

}
