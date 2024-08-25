package com.project.bookbook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookbook.domain.entity.ImageEntity;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Long> {
}