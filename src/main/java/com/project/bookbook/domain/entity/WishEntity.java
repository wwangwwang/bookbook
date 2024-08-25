package com.project.bookbook.domain.entity;

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
@Table(name = "wish")
public class WishEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long wishNum; // 찜번호

	@ManyToOne // FK 단방향
	@JoinColumn(name = "userId", nullable = false)
	private UserEntity user; // 사용자ID fk

	@ManyToOne // FK 단방향
	@JoinColumn(name = "bookNum", nullable = false)
	private BookEntity book; // ISBN fk

}