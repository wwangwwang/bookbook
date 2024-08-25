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
@Table(name = "review")
public class ReviewEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reviewNum; // 리뷰 번호
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "userId", nullable = false)
	private UserEntity user; // 사용자ID fk
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "bookNum", nullable = false)
	private BookEntity book ; // 도서번호 fk
	
	@Column(nullable=false)
	private String reviewContent; //내용
	
	@CreationTimestamp
	@Column(columnDefinition = "timestamp")
	private LocalDateTime reviewDate; //작성일자
	
	@Column(nullable=false)
	private int rate; //별점 (1~5)
	
	@ColumnDefault("0")
	private int complaint; //신고누적횟수
	
	@ColumnDefault("0")
	private int recommend; //추천개수
	
	@ColumnDefault("0")
	private int actualOrder; //구매자여부 (0:구매자 아님, 1:구매자)

}
