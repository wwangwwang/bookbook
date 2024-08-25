package com.project.bookbook.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookbook.domain.entity.ApprovalStatus;
import com.project.bookbook.domain.entity.SellerEntity;

public interface SellerEntityRepository extends JpaRepository<SellerEntity, Long> {
	Optional<SellerEntity> findByBusinessNum(String businessNum);

	List<SellerEntity> findByApprovalStatus(ApprovalStatus pending);
	
	List<SellerEntity> findByShopNameContaining(String shopName);
}