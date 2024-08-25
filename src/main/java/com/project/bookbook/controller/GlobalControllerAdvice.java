package com.project.bookbook.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.FavoriteService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@ControllerAdvice(annotations = Controller.class)
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final FavoriteService favoriteService;
    
    @ModelAttribute
    public void addAttributes(Model model, @AuthenticationPrincipal CustomUserDetails user, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        if (requestUri.startsWith("/mypage/") && user != null) {
            favoriteService.findByUser(model, user); // 찜 개수
        }
    }
}