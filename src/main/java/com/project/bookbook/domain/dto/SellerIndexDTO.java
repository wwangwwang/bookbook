package com.project.bookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerIndexDTO {

	private String bookName;           // 책 제목
	private String author;          // 저자
	private String pubdate;         // 출간일
	
	
}
