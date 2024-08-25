package com.project.bookbook.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bookbook.domain.entity.CartDetailEntity;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetailEntity, Long> {
    Optional<CartDetailEntity> findByCart_CartNumAndBook_BookNum(Long cartNum, Long bookNum);
}
