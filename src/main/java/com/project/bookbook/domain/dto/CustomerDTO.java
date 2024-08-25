package com.project.bookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

	private long userId; // 사용자ID
	private String userName; // 사용자이름
	private String userRRN; // 주민등록번호
	private String email; // 이메일
	private String gender; // 성별
	private String phoneNumber; // 핸드폰번호
	private String password; // 비밀번호
	private String birthDate; // 생년월일
	private String postcode; // 우편번호
	private String address; // 주소
	private String extraAddress; // 참고항목
	private String detailAddress; // 상세주소
	private long status; // 회원상태 
	
}
