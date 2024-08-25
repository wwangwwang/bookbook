package com.project.bookbook.domain.dto;

import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.entity.CartEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDTO {
	
	private long cartDetailNum;
	private long cartCnt;
	private long bookNum ;
	private String bookName ;
	private String bookImg ;
	private String author ;
	private int discount ;
	private int stock ;

}
