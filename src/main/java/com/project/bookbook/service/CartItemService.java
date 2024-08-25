package com.project.bookbook.service;

import com.project.bookbook.domain.entity.BookEntity;

public interface CartItemService {

	void addToCart(BookEntity book, Long userId, int quantity);

}
