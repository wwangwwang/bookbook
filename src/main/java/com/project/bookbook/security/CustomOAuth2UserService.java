package com.project.bookbook.security;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.bookbook.domain.entity.Role;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	 private final PasswordEncoder passwordEncoder;
	 private final UserEntityRepository userRepository;
	    
	    // 소셜 로그인 시 사용자의 정보를 로드하는 메서드
	    @Override
	    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	    	// OAuth2 인증 서버에서 사용자 정보를 가져옴
	        OAuth2User oAuth2User = super.loadUser(userRequest);
	        String registrationId = userRequest.getClientRegistration().getRegistrationId();
	        return processSocialLogin(oAuth2User, registrationId);
	    }
	    
	    // 소셜 로그인 처리 메서드: 사용자가 이미 존재하는지 확인하고, 없으면 새로 생성
	    private OAuth2User processSocialLogin(OAuth2User oAuth2User, String registrationId) {
	        String email = extractEmail(oAuth2User, registrationId);
	        String name = extractName(oAuth2User, registrationId);

	        UserEntity user = userRepository.findByEmail(email)
	                .orElseGet(() -> createSocialUser(email, name));

	        return new CustomUserDetails(user);
	    }
	    
	    // 소셜 로그인 플랫폼에 따라 이메일 정보를 추출하는 메서드
	    private String extractEmail(OAuth2User oAuth2User, String registrationId) {
	        if ("google".equals(registrationId)) {
	            return oAuth2User.getAttribute("email");
	        } else if ("naver".equals(registrationId)) {
	            Map<String, Object> response = oAuth2User.getAttribute("response");
	            return String.valueOf(response.get("email"));
	        } else if ("kakao".equals(registrationId)) {
	            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
	            return String.valueOf(kakaoAccount.get("email"));
	        }
	        throw new OAuth2AuthenticationException("Unsupported registration ID");
	    }
	    
	    // 소셜 로그인 플랫폼에 따라 사용자 이름을 추출하는 메서드	    
	    private String extractName(OAuth2User oAuth2User, String registrationId) {
	        if ("google".equals(registrationId)) {
	            return oAuth2User.getAttribute("name");
	        } else if ("naver".equals(registrationId)) {
	            Map<String, Object> response = oAuth2User.getAttribute("response");
	            return String.valueOf(response.get("name"));
	        } else if ("kakao".equals(registrationId)) {
	            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
	            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
	            return String.valueOf(profile.get("nickname"));
	        }
	        throw new OAuth2AuthenticationException("Unsupported registration ID");
	    }
	    
	    private UserEntity createSocialUser(String email, String name) {
	        UserEntity entity = UserEntity.builder()
	                .email(email)
	                .userName(name)
	                .password(passwordEncoder.encode(String.valueOf(System.currentTimeMillis())))
	                .userRRN("소셜로그인")
	                .gender("미입력")
	                .phoneNumber("미입력")
	                .birthDate("미입력")
	                .postcode("미입력")
	                .address("미입력")
	                .extraAddress("미입력")
	                .detailAddress("미입력")
	                .status(0L)
	                .build()
	                .addRole(Role.USER);

	        return userRepository.save(entity);
	    }
	}