package com.project.bookbook.domain.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetailDTO {
	
	private long merchantUid;
	private long couponNum;
	private int paidAmount;
	private LocalDateTime orderDate;
	private int orderStatus;
	
	public String dateFormatting() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. M. d");
        return orderDate.format(formatter);
	}

}
