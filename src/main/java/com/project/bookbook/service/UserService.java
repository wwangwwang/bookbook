package com.project.bookbook.service;

import com.project.bookbook.domain.dto.AdditionalUserInfoDTO;
import com.project.bookbook.domain.dto.UserSaveDTO;
import com.project.bookbook.domain.entity.Role;

public interface UserService {

	void signupProcess(UserSaveDTO dto, Role role);

	void updateAdditionalInfo(long userId, AdditionalUserInfoDTO additionalInfo);

	boolean isEmailDuplicate(String email);

}