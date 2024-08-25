package com.project.bookbook.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    
    // 관리자 로그인을 위한 보안
    @Bean
    @Order(1)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/login/admin/**")
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()
            )
            .formLogin(login -> login
                .loginPage("/login/admin")
                .loginProcessingUrl("/login/admin")	                
                .failureHandler(customAuthenticationFailureHandler) 
                .usernameParameter("businessNum")
                .passwordParameter("password")
                .successHandler(customLoginSuccessHandler)
                .permitAll()
            );
        return http.build();
    }
    
    // 사용자 로그인을 위한 보안
    @Bean
    @Order(2)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
            	// 공개 접근 허용 URL 설정           		
                .requestMatchers("/", "/signup/**", "/login/**", "/logout/**", "/bookList", "/detail/**","/api/**","/event", "/additional-info", "/bookBot/**","/api/upload", "/upload-image", "/search","/approve").permitAll()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/seller/**").hasRole("SELLER")
                .requestMatchers("/mypage/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureHandler(customAuthenticationFailureHandler)
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(customLoginSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            //GET 요청을 통해 로그아웃을 처리하도록 허용
            .logout(logout -> logout.logoutRequestMatcher(
            		 new OrRequestMatcher(
	    			        new AntPathRequestMatcher("/logout", "GET"),
	    			        new AntPathRequestMatcher("/logout", "POST")
	    			    )
            ))
            .userDetailsService(customUserDetailsService)
            // OAuth2 로그인 설정
            .oauth2Login(oauth2 -> oauth2
                    .loginPage("/login")
                    .userInfoEndpoint(userInfo -> userInfo
                        .userService(customOAuth2UserService)
                    )
                    .successHandler(customLoginSuccessHandler)
                    .failureHandler((request, response, exception) -> {
                        // OAuth2 로그인 실패 시 처리
                        response.sendRedirect("/login?error=oauth2");
                    })
                );
            
            return http.build();
        }
    }
    
