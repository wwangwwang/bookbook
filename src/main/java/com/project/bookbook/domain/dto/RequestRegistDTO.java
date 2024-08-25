package com.project.bookbook.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.bookbook.domain.entity.BookEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestRegistDTO {

    
    private String bookName; // 도서명
    private String isbn;
    //private String bookImg; //도서 표지;
    private int stock; // 기본값 0 설정
    private int discount; // 판매가
    private String author; // 저자
    private String pubdate; // 출간일자
    private String publisher; // 출판사
    private String link; // 상세정보 url
    private String description; // 책 소개
    
    private String tempKey; // 책 소개
    private String orgName; // 책 소개
   
    public LocalDateTime convertToLocalDateTime() {
        // 문자열을 LocalDate로 변환
        LocalDate localDate = LocalDate.parse(pubdate, DateTimeFormatter.ISO_LOCAL_DATE);
        
        // LocalDate를 LocalDateTime으로 변환 (시간 부분은 00:00:00으로 설정)
        LocalDateTime localDateTime = localDate.atStartOfDay();
        
        return localDateTime;
    }
	
	// 문자열을 LocalDateTime으로 변환
    public LocalDateTime getPubdateAsLocalDateTime() {
        if (pubdate == null || pubdate.isEmpty()) {
            return null; // 또는 적절한 기본값 설정
        }
        // 문자열 포맷에 맞는 DateTimeFormatter 사용 (예: "yyyy-MM-dd'T'HH:mm:ss")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(pubdate, formatter);
    }
	
    public BookEntity toRequest(String uploadKey) {
        return BookEntity.builder()
                .bookImg(uploadKey)
                .bookName(bookName)
                .discount(discount)
                .pubdate(convertToLocalDateTime())
                .link(link)
                .description(description)
                .author(author)
                .publisher(publisher)
                .stock(stock)
                .build();
    }
    
    public BookEntity toBookEntity(String uploadUrl) {
        return BookEntity.builder()
                .bookImg(uploadUrl)
                .bookName(bookName)
                .isbn(isbn)
                .discount(discount)
                .pubdate(convertToLocalDateTime())
                .link(link)
                .description(description)
                .author(author)
                .publisher(publisher)
                .stock(stock)
                .build();
    }
	
}
