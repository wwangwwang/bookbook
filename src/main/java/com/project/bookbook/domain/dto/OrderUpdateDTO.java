package com.project.bookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDTO {

	private long merchantUid; // 주문번호 fk
	private String orderStatus; //주문상태
}
