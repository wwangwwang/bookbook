package com.project.bookbook.domain.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDTO {
	private Long reviewNum;
    private String username;
    private String bookname;
    private String author;
    private String isbn;
    private String reviewContent;
    private int rate;
    private LocalDateTime reviewDate;
    private int recommend;
    private int complaint;
    private boolean actualOrder;
}