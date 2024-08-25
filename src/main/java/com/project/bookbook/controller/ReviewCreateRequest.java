package com.project.bookbook.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequest {
	private String reviewContent;
    private int rate;
    
    @Override
    public String toString() {
        return "ReviewCreateRequest{content='" + reviewContent + "', rate=" + rate + "}";
    }
}