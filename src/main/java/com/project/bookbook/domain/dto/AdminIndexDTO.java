package com.project.bookbook.domain.dto;

import java.time.LocalDateTime;

import com.project.bookbook.domain.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminIndexDTO {

	private long qnaNum;
	private UserEntity user; // 사용자ID fk
	private String title;
	private String content;
	private String qnaType; //상품, 배송, 오류
	private LocalDateTime date;
	private int status; //0: 답변대기, 1: 답변완료
}
