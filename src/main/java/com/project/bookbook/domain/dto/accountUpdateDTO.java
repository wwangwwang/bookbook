package com.project.bookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class accountUpdateDTO {
	private long userId; // 사용자ID
	private String userName; // 사용자이름
	private String phoneNumber; // 핸드폰번호
	private String postcode; // 우편번호
	private String address; // 주소
	private String extraAddress; // 참고항목
	private String detailAddress; // 상세주소
}
