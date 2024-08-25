package com.project.bookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MypageUserDTO {
	
	private String userName;
	private String userRRN;
	private String gender;
	private String email;
	private String phoneNumber;
	private String birthDate;
	private String postcode;
	private String address;
	private String extraAddress;
	private String detailAddress;
	private long status;
	
}
