package com.project.bookbook.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bookbook.domain.entity.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
	 Optional<CartEntity> findByUser_UserId(Long userId);
}
