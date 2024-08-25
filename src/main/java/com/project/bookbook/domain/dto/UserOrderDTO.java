package com.project.bookbook.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderDTO {
	
	private long merchantUid;
	private LocalDateTime orderDate;
	private int orderStatus;
	private int paidAmount;
	private String BookImg;
	private String BookName;

}
