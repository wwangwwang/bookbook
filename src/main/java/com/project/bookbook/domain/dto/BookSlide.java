package com.project.bookbook.domain.dto;

import java.util.List;


public class BookSlide {
	private List<BookDTO> books;
	
	
	 public BookSlide(List<BookDTO> books) {
		    this.books = books;
     }

     public List<BookDTO> getBooks() {
         return books;
     }

}
