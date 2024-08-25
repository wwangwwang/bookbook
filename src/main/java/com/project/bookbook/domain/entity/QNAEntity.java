package com.project.bookbook.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "qna")
public class QNAEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long qnaNum; 
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "userId", nullable = false)
	private UserEntity user; // 사용자ID fk
	
	@Column(nullable = false)
	private String title; 
	
	@Column(nullable = false)
	private String content; 
	
	@Column(nullable = false)
	private String qnaType; //상품, 배송, 오류
	
	@CreationTimestamp
	@Column(columnDefinition = "timestamp")
	private LocalDateTime date;
	
	@Column
	@ColumnDefault("0")
	private int status; //0: 답변대기, 1: 답변완료

}
