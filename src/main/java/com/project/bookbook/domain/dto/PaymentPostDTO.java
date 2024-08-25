package com.project.bookbook.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPostDTO {
	
	private long merchantUid;
	private long couponNum;
	private int amount;
	private int couponRate;
	private int paidAmount;

}
