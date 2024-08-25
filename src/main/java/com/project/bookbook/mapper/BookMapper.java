package com.project.bookbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.project.bookbook.domain.dto.BookDTO;

@Mapper
public interface BookMapper {
	List<BookDTO> findAll();

	BookDTO findIsbn(String isbn);

}
