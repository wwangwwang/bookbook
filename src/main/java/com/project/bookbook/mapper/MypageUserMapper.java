package com.project.bookbook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.project.bookbook.domain.dto.MypageUserDTO;
import com.project.bookbook.domain.dto.accountUpdateDTO;

@Mapper
public interface MypageUserMapper {

	MypageUserDTO findById(long userId);

	void updateId(accountUpdateDTO dto);

	void changeStatus(long userId);
	
	@Select("SELECT s.shop_name FROM seller s JOIN user u ON s.seller_id = u.seller_id WHERE u.user_id = #{userId}")
	String getSellerName(@Param("userId") long userId);
	
	@Select("select seller_id from user where user_id = #{userId}")
	long getSellerId(long userId);

}
