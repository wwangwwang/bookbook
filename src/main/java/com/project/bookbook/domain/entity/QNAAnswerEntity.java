package com.project.bookbook.domain.entity;

import java.time.LocalDateTime;

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
@Table(name = "qna_answer")
public class QNAAnswerEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long qnaAnswerNum; //답변 번호
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "qnaNum", nullable = false)
	private QNAEntity qna; // 질문 fk
	
	@Column(nullable = false)
	private String title; //답변 제목
	
	@Column(nullable = false)
	private String content; //답변 내용
	
	@CreationTimestamp
	@Column(columnDefinition = "timestamp")
	private LocalDateTime date; //작성날짜

}