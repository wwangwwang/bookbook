package com.project.bookbook.security;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.UserEntityRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private final UserEntityRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            UserEntity user = userDetails.getUser();

            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            if (isAdditionalInfoNeeded(user)) {
                response.sendRedirect("/additional-info");
            } else if (roles.contains("ROLE_ADMIN")) {
                response.sendRedirect("/admin");
            } else if (roles.contains("ROLE_SELLER")) {
                response.sendRedirect("/seller");
            } else {
                response.sendRedirect("/"); // 일반 사용자의 경우 메인 페이지로
            }
        } else {
            response.sendRedirect("/");
        }
    }
    // 사용자의 추가 정보 입력 필요 여부를 확인하는 메소드
    private boolean isAdditionalInfoNeeded(UserEntity user) {
        return user.getUserRRN().equals("소셜로그인") ||
                user.getGender().equals("미입력") ||
                user.getPhoneNumber().equals("미입력") ||
                user.getBirthDate().equals("미입력") ||
                user.getPostcode().equals("미입력") ||
                user.getAddress().equals("미입력") ||
                user.getExtraAddress().equals("미입력") ||
                user.getDetailAddress().equals("미입력");
    }
}