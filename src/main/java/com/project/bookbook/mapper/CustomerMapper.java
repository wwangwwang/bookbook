package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.project.bookbook.domain.dto.CustomerDTO;

@Mapper
public interface CustomerMapper {

	@Select("select * from user order by user_id desc")
	List<CustomerDTO> findCustomer();
	
	@Select("select * from user where user_Id=#{userId}")
	CustomerDTO findCustomerDetail(@Param("userId") long userId);
	
}
