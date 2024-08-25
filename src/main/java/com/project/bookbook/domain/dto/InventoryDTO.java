package com.project.bookbook.domain.dto;

import java.time.LocalDateTime;

import com.project.bookbook.domain.entity.CategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {

	private long bookNum; //도서번호
	private CategoryEntity category; //카테고리 fk
	private String bookName; //도서 제목
	private String bookImg; //도서 표지
	private String author; //저자
	private String publisher; //출판사
	private LocalDateTime pubdate; //출간일자
	private String description; //책 소개
	private String isbn; //isbn 책번호
	private String link; //상세정보 url
	private int discount; //판매가
	private int stock; //재고
}
