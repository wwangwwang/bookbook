package com.project.bookbook.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.bookbook.domain.dto.AdditionalUserInfoDTO;
import com.project.bookbook.domain.dto.UserSaveDTO;
import com.project.bookbook.domain.entity.Role;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.UserEntityRepository;
import com.project.bookbook.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceProcess implements UserService{
	
	private final UserEntityRepository repository;
	private final PasswordEncoder pe;
	
	@Override
	public void signupProcess(UserSaveDTO dto, Role role) {
		UserEntity userEntity = dto.toEntity(pe);
        userEntity.addRole(role);
        repository.save(userEntity);
    }

	@Override
	public void updateAdditionalInfo(long userId, AdditionalUserInfoDTO additionalInfo) {
		UserEntity user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setUserRRN(additionalInfo.getUserRRN());
        user.setGender(additionalInfo.getGender());
        user.setPhoneNumber(additionalInfo.getPhoneNumber());
        user.setBirthDate(additionalInfo.getBirthDate());
        user.setPostcode(additionalInfo.getPostcode());
        user.setAddress(additionalInfo.getAddress());
        user.setExtraAddress(additionalInfo.getExtraAddress());
        user.setDetailAddress(additionalInfo.getDetailAddress());

        repository.save(user);
    }
	@Override
    public boolean isEmailDuplicate(String email) {
        return repository.findByEmail(email).isPresent();
    }
}