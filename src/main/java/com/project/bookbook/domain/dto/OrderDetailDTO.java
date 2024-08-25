package com.project.bookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
	
	private long bookNum ;
	private int orderCnt;
	private String bookName ;
	private String bookImg ;
	private String author ;
	private int discount ;
	private int stock ;
	
}
