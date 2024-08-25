package com.project.bookbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.bookbook.domain.dto.FavoriteListDTO;

@Mapper
public interface FavoriteMapper {

	List<FavoriteListDTO> findByUser(long userId);

	void deleteFavorite(Map<String, Long> params);

}
