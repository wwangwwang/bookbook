package com.project.bookbook.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.bookbook.domain.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

	Optional<BookEntity> findByIsbn(String isbn);

	@Query("SELECT b.bookNum FROM BookEntity b WHERE b.isbn = :isbn")
	Optional<Long> findBookNumByIsbn(@Param("isbn") String isbn);

}
