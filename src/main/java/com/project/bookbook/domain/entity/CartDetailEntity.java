package com.project.bookbook.domain.entity;

import org.hibernate.annotations.ColumnDefault;

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
@Table(name = "cart_detail")
public class CartDetailEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cartDetailNum; //장바구니 상세번호
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "bookNum", nullable = false)
	private BookEntity book ; // 도서번호 fk
	
	@ManyToOne // FK 단방향
	@JoinColumn(name = "cartNum", nullable = false)
	private CartEntity cart ; // 장바구니 번호 fk
	
	@ColumnDefault("1")
	private long cartCnt; //상품수량 

}
