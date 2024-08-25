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
public class QNADTO {
	private long qnaNum;
	private UserEntity user;
	private String title;
	private String content;
	private LocalDateTime date;
	private int status;
}
