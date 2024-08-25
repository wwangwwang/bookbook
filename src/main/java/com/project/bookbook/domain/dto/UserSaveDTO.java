package com.project.bookbook.domain.dto;



import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.bookbook.domain.entity.Role;
import com.project.bookbook.domain.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class UserSaveDTO {
	
	private long userId;
    private String userName; // 사용자이름
    private String userRRN; // 주민등록번호
    private String gender; // 성별
    private String email; // 이메일 (선택입력)
    private String phoneNumber; // 핸드폰번호
    private String password; // 비밀번호
    private String birthDate; // 생년월일
    private String postcode; // 우편번호
    private String address; // 주소
    private String extraAddress; // 참고항목
    private String detailAddress; // 상세주소

	
	public UserEntity toEntity(PasswordEncoder pe) {
		return UserEntity.builder()
				.userId(userId)
				.userName(userName)
				.userRRN(userRRN)
				.gender(gender)
				.email(email)
				.password(pe.encode(password))
				.phoneNumber(phoneNumber)
				.birthDate(birthDate)
				.postcode(postcode)
				.address(address)
				.extraAddress(extraAddress)
				.detailAddress(detailAddress)
				.build();
	}
	
	
	
}