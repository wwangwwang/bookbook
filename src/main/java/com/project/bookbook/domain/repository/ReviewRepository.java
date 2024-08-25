package com.project.bookbook.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookbook.domain.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{

	List<ReviewEntity> findByBookIsbnOrderByReviewDateDesc(String isbn);

	List<ReviewEntity> findByBookIsbn(String isbn);

	int countByBookIsbn(String isbn);

}
