package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.project.bookbook.domain.dto.InventoryDTO;

@Mapper
public interface InventoryMapper {

	@Select("select * from book order by book_num desc")
	List<InventoryDTO> findBook();

	@Delete("delete from book where book_num = #{bookNum};")
	void deleteBook(@Param("bookNum") long bookNum);
}
