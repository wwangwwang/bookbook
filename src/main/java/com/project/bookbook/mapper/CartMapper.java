package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.bookbook.domain.dto.CartBookCntDTO;
import com.project.bookbook.domain.dto.CartDetailDTO;
import com.project.bookbook.domain.dto.UpdateCartDTO;

@Mapper
public interface CartMapper {

	List<CartDetailDTO> findAllCartDetail(long userId);

	void deleteCartDetail(long cartDetailNum);

	List<CartBookCntDTO> selectCartDetailsByIds(List<Long> cartDetailNums);

	long findCartNum(long userId);

	void deleteAllCart(long cartNum);

	void updateCartItemQuantity(UpdateCartDTO dto);

}