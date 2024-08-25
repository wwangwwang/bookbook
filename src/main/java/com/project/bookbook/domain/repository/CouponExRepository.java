package com.project.bookbook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookbook.domain.entity.CouponEntity;

public interface CouponExRepository extends JpaRepository<CouponEntity, String>{

}
