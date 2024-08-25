package com.project.bookbook.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookbook.domain.dto.CombinedSellerDTO;
import com.project.bookbook.domain.entity.ApprovalStatus;
import com.project.bookbook.domain.entity.ImageEntity;
import com.project.bookbook.domain.entity.Role;
import com.project.bookbook.domain.entity.SellerEntity;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.SellerEntityRepository;
import com.project.bookbook.domain.repository.UserEntityRepository;
import com.project.bookbook.service.ImageService;
import com.project.bookbook.service.SellerService;
import com.project.bookbook.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerServiceProcess implements SellerService {
	
	private final UserEntityRepository userRepository;
    private final SellerEntityRepository sellerRepository;
    private final PasswordEncoder pe;
    private final ImageService imageService;

    @Override
    @Transactional
    public void signupProcess(CombinedSellerDTO dto) {
    	
        // 비밀번호 암호화
        String encodedPassword = pe.encode(dto.getPassword());
        dto.setPassword(encodedPassword);
        
        // UserEntity 생성 및 저장
        UserEntity userEntity = dto.toUserEntity();
        userEntity.addRole(Role.SELLER);
        userEntity = userRepository.save(userEntity);

        // SellerEntity 생성 및 저장 (User 정보 포함)
        SellerEntity sellerEntity = dto.toSellerEntity();
        sellerEntity = sellerRepository.save(sellerEntity);
        sellerEntity.setApprovalStatus(ApprovalStatus.PENDING);

        // UserEntity에 SellerEntity 연결
        userEntity.setSeller(sellerEntity);
        userRepository.save(userEntity);
        
        // 사업자 등록증 이미지 처리
        if (dto.getBusinessRegImageId() != null) {
        	
            // 이미지 서비스를 통해 해당 ID의 이미지 엔티티를 조회
            ImageEntity image = imageService.getImageById(dto.getBusinessRegImageId());
            
            // 조회한 이미지 엔티티를 판매자 엔티티의 사업자 등록증 이미지로 설정
            // 이는 판매자 엔티티와 이미지 엔티티 간의 관계를 설정
            sellerEntity.setBusinessRegImage(image);
            
            // 이미지 엔티티의 파일 URL을 판매자 엔티티의 사업자 등록증 URL로 설정
            // 이를 통해 실제 이미지 파일에 쉽게 접근할 수 있음           
            sellerEntity.setBusinessReg(image.getFileUrl());
        }

    }

    // 승인 대기 조회
	@Override
	public List<SellerEntity> getPendingSellers() {
		return sellerRepository.findByApprovalStatus(ApprovalStatus.PENDING);
	}
	
	//판매자 승인 처리
	@Override
	public void approveSeller(Long id) {
		 SellerEntity seller = sellerRepository.findById(id)
		            .orElseThrow(() -> new RuntimeException("Seller not found"));
		        seller.setApprovalStatus(ApprovalStatus.APPROVED);
		        sellerRepository.save(seller);
		    }

	@Override
	public void rejectSeller(Long id) {
		SellerEntity seller = sellerRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Seller not found"));
	        seller.setApprovalStatus(ApprovalStatus.REJECTED);
	        sellerRepository.save(seller);
	    }

	
	@Override
	public List<SellerEntity> getAllSellers() {
		return sellerRepository.findAll();
	}

	// ID로 특정 판매자 조회
	@Override
	public SellerEntity getSellerById(Long id) {
		return sellerRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Seller not found"));
	}


	@Override
	public List<SellerEntity> findSellersByShopName(String shopName) {
		return sellerRepository.findByShopNameContaining(shopName);
	}
	
	// 회원 삭제
	@Override
	public void deleteSeller(Long id) {
		 SellerEntity seller = sellerRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Seller not found"));
	        
	        // 연관된 UserEntity 가져오기
	        UserEntity user = seller.getUser();
	        if (user != null) {
	            user.setSeller(null);  // UserEntity와 SellerEntity의 관계 해제
	            userRepository.save(user);
	        }
	        
	        // 사업자 등록증 이미지 삭제
	        if (seller.getBusinessRegImage() != null) {
	            imageService.deleteImage(seller.getBusinessRegImage().getId());
	        }
	        
	        // SellerEntity 삭제
	        sellerRepository.delete(seller);
	    }

	 @Override
	    public boolean isEmailDuplicate(String email) {
	        return userRepository.findByEmail(email).isPresent();
	    }

	    @Override
	    public boolean isBusinessNumDuplicate(String businessNum) {
	        return sellerRepository.findByBusinessNum(businessNum).isPresent();
	    }
	
}