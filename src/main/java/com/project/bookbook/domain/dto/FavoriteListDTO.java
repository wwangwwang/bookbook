package com.project.bookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteListDTO {
	
	private long bookNum;
	private String author;
	private String bookImg;
	private String bookName;

}
