package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.project.bookbook.domain.dto.AdminIndexDTO;
import com.project.bookbook.domain.dto.AdminOrderDTO;
import com.project.bookbook.domain.dto.ReviewDTO;

@Mapper
public interface AdminMapper {

	@Select("select * from qna order by qna_num desc")
	List<AdminIndexDTO> find();
	
	List<ReviewDTO> findAllReviews();

	void deleteReview(Long reviewNum);

	List<ReviewDTO> findTop8Reviews();

	
	@Select("select * from user_orders order by merchant_uid desc")
	List<AdminOrderDTO> findOrder();

	@Update("update qna set qna_num = #{qnaNum}, status = 1 where qna_num = #{qnaNum}")
	void findInquiryUpdate(long qnaNum);
}
