package com.project.bookbook.domain.dto;

import lombok.Data;

@Data
public class AdditionalUserInfoDTO {
	private String userRRN;
    private String gender;
    private String phoneNumber;
    private String birthDate;
    private String postcode;
    private String address;
    private String extraAddress;
    private String detailAddress;
}