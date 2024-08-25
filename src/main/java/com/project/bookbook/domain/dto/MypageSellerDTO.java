package com.project.bookbook.domain.dto;

import com.project.bookbook.domain.entity.ApprovalStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageSellerDTO {

	private Long sellerId;
	private String shopName;
	private String telNum;
	private String businessNum;
	private String businessReg; 
	private String bank;
	private String account; 
	private String accountHolder;
	private String settlementAmount;
	private String businessRegCopy;
	private String userName;
	private String userRRN;
	private String gender;
	private String email;
	private String phoneNumber;
	private String password;
	private String birthDate;
	private String postcode;
	private String address;
	private String extraAddress;
	private String detailAddress;
	private Long status;
	private ApprovalStatus approvalStatus;
}
