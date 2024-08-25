package com.project.bookbook.domain.entity;

import com.project.bookbook.domain.entity.NNPIntentionEntity;
import com.project.bookbook.domain.entity.VVIntentionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ex_keyword")
public class EXKeywordEntity {

    @Id
    @Column(nullable = false)
    private String keyword; // 키워드 값

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vv_no")
    private VVIntentionEntity vvIntention; // 해당 키워드와 연관된 의도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nnp_no")
    private NNPIntentionEntity nnpIntention; // 해당 키워드와 연관된 의도
}