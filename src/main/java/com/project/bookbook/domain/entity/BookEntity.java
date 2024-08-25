package com.project.bookbook.domain.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name="book")
public class BookEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookNum; //도서번호

    @ManyToOne // FK 단방향
    @JoinColumn(name = "id", nullable = true)
    private CategoryEntity category; //카테고리 fk

    @Column(nullable = false)
    private String bookName; //도서 제목

    @Column(nullable = false)
    private String bookImg; //도서 표지

    private String author; //저자

    private String publisher; //출판사

    @CreationTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime pubdate; //출간일자

    private String description; //책 소개

    private String isbn; //isbn 책번호

    private String link; //상세정보 url

    private int discount; //판매가

    @ColumnDefault("100")
    private int stock; //재고

}
