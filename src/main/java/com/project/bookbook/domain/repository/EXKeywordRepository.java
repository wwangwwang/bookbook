package com.project.bookbook.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookbook.domain.entity.EXKeywordEntity;

public interface EXKeywordRepository extends JpaRepository<EXKeywordEntity, String> {
    Optional<EXKeywordEntity> findByKeyword(String keyword);
}
