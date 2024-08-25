package com.project.bookbook.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.CouponListDTO;
import com.project.bookbook.domain.entity.CouponEntity;
import com.project.bookbook.domain.entity.UserCouponEntity;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.CouponRepository;
import com.project.bookbook.domain.repository.UserCouponRepository;
import com.project.bookbook.domain.repository.UserRepository;
import com.project.bookbook.mapper.CouponMapper;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.CouponService;
import com.project.bookbook.service.UserCouponService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCouponServiceProcess implements UserCouponService{
	
	private final CouponRepository couponRepository; // CouponRepository를 주입받아야 합니다.
	private final UserRepository userRepository;
    private final UserCouponRepository userCouponRepository;

	@Override
    public void list(Model model) {
        List<CouponEntity> couponEntities = couponRepository.findAll();
        
        List<CouponListDTO> couponDTOs = couponEntities.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        model.addAttribute("coupons", couponDTOs);
    }

    @Override
    public CouponListDTO getCouponById(String id) {
        CouponListDTO result = couponRepository.findById(id)
            .map(this::convertToDTO)
            .orElse(null);
        
        if (result != null) {
            System.out.println("쿠폰 조회 성공: " + result.getCouponName());
        } else {
            System.out.println("해당 ID의 쿠폰을 찾을 수 없음: " + id);
        }
        
        return result;
    }

    private CouponListDTO convertToDTO(CouponEntity entity) {
        CouponListDTO dto = CouponListDTO.builder()
            .couponNum(entity.getCouponNum())
            .couponName(entity.getCouponName())
            .couponRate(entity.getCouponRate())
            .couponDetail(entity.getCouponDetail())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .build();
        return dto;
    }

	@Override
	public UserCouponEntity assignCouponToUser(String couponId, long userId) {
		 System.out.println("사용자에게 쿠폰 할당 시작 - 쿠폰 ID: " + couponId + ", 사용자 ID: " + userId);
	        
	        CouponEntity coupon = couponRepository.findById(couponId)
	            .orElseThrow(() -> new RuntimeException("쿠폰을 찾을 수 없습니다: " + couponId));
	        
	        UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));
	        
	        UserCouponEntity userCoupon = UserCouponEntity.builder()
	            .user(user)
	            .coupon(coupon)
	            .status(0)  // 0: 사용 전
	            .build();
	        
	        UserCouponEntity savedUserCoupon = userCouponRepository.save(userCoupon);
	        System.out.println("쿠폰이 사용자에게 성공적으로 할당됨 - UserCoupon ID: " + savedUserCoupon.getUserCouponNum());
	        
	        return savedUserCoupon;
	    }
}
