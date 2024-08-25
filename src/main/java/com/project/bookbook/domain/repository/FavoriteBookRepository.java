package com.project.bookbook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.entity.FavoriteBook;

public interface FavoriteBookRepository extends JpaRepository<FavoriteBook, String>{



}
