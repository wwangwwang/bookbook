package com.project.bookbook.domain.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverTokenDTO {
	
	private String access_token;
	private String refresh_token;
	private String expires_in;
	private String scope;
	private String token_type;
	
}
