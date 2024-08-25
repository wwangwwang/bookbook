package com.project.bookbook.security;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.bookbook.domain.entity.ApprovalStatus;
import com.project.bookbook.domain.entity.Role;
import com.project.bookbook.domain.entity.SellerEntity;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.SellerEntityRepository;
import com.project.bookbook.domain.repository.UserEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	

    private final UserEntityRepository userRepository;
    private final SellerEntityRepository sellerRepository;
    // 이메일(일반 사용자) 또는 사업자 번호(판매자)로 사용자 조회
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username)
                .orElseGet(() -> sellerRepository.findByBusinessNum(username)
                        .map(SellerEntity::getUser)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username)));
       

        // 판매자인 경우 승인 상태 확인
        if (user.getRoles().contains(Role.SELLER)) {
            SellerEntity seller = sellerRepository.findByBusinessNum(username)
                .orElseThrow(() -> new UsernameNotFoundException("Seller not found with business number: " + username));

            if (seller.getApprovalStatus() != ApprovalStatus.APPROVED) {
                throw new DisabledException("Seller account is:" + seller.getApprovalStatus());
            }
        }
        
        //status가 0이 아니라면 탈퇴처리된 회원
        if(user.getStatus() != 0) {
        	throw new DisabledException("delete");
        }
        
        return new CustomUserDetails(user);
    }
}