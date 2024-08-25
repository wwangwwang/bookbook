package com.project.bookbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class BookbookbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookbookbookApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(14);
	}
	
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
	    return new HiddenHttpMethodFilter();
	}
}
