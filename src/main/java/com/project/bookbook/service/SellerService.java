package com.project.bookbook.service;

import java.util.List;

import com.project.bookbook.domain.dto.CombinedSellerDTO;
import com.project.bookbook.domain.entity.SellerEntity;

public interface SellerService {

	void signupProcess(CombinedSellerDTO dto);

	List<SellerEntity> getPendingSellers();
	void approveSeller(Long id);
	void rejectSeller(Long id);

	List<SellerEntity> getAllSellers();
	SellerEntity getSellerById(Long id);
	
	List<SellerEntity> findSellersByShopName(String shopName);

	void deleteSeller(Long id);
	
	
	boolean isEmailDuplicate(String email);
    boolean isBusinessNumDuplicate(String businessNum);

}