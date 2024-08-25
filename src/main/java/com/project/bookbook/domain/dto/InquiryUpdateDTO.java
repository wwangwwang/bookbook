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
public class InquiryUpdateDTO {

	private long qnaNum; // 주문번호 fk
	private int Status; //주문상태
}
