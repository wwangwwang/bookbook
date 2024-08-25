package com.project.bookbook.domain.dto;

import java.time.LocalDateTime;

import com.project.bookbook.domain.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDTO {

	private long qnaNum;
	private String title;
	private String qnaType; //상품, 배송, 오류
	private UserEntity user; // 사용자ID fk
	private LocalDateTime date;
	private int status; //0: 답변대기, 1: 답변완료
	private String content; 
}
