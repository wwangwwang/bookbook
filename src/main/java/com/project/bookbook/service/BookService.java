package com.project.bookbook.service;

import java.util.List;

import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.BookDTO;
import com.project.bookbook.domain.dto.BookSearchResponse.Item;

public interface BookService {

	//void searchBooks(String string, Model model);


	void getBookList(Model model);


	//List<BookDTO> searchBooks(String query);

	Item getBookByIsbn(String isbn);

	void searchBooks(String query, Model model);


	void getDefaultBooks(Model model);


	void addToFavorites(String isbn) throws Exception;


	void addToCart(String isbn, Long userId, int quantity);
	void addToWishlist(String isbn, Long userId) throws Exception;


	void getNewBook(Model model);


	void getNewIsbn(String isbn, Model model);






	


	//void addToCart(String isbn);


	 




}