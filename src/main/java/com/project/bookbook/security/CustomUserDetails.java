package com.project.bookbook.security;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User; // 수정된 부분
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.project.bookbook.domain.entity.UserEntity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;

@Getter
// principle 객체
public class CustomUserDetails extends User implements OAuth2User {

	private static final long serialVersionUID = 1L;
	private String email;
	private String name;
	private UserEntity userEntity; // UserEntity 참조 추가
	private long userId;
	private String password;
	private long status;
	private String phoneNumber;

	private Map<String, Object> attributes;

	public CustomUserDetails(UserEntity entity) {
		super(entity.getEmail(), entity.getPassword(), entity.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet()));
		this.email = entity.getEmail();
		this.name = entity.getUserName();
		this.userEntity = entity; // UserEntity 저장
		this.userId = entity.getUserId();
		this.password = entity.getPassword();
		this.status = entity.getStatus();
		this.phoneNumber = entity.getPhoneNumber();

	}

	// UserEntity 반환 메소드 추가
	public UserEntity getUser() {
		return this.userEntity;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}
}