package com.project.bookbook;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.bookbook.domain.entity.CouponEntity;
import com.project.bookbook.domain.entity.Role;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.CouponExRepository;
import com.project.bookbook.domain.repository.UserEntityRepository;

@SpringBootTest
class BookbookbookApplicationTests {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserEntityRepository mRepository;
    
}
