package com.project.bookbook.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookbook.domain.entity.AnswerEntity;
import com.project.bookbook.domain.entity.NNPIntentionEntity;
import com.project.bookbook.domain.entity.VVIntentionEntity;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Integer> {

	Optional<AnswerEntity> findByVvIntention_VvNoAndNnpIntention_NnpNo(int vvNo, int nnpNo);

	
}
