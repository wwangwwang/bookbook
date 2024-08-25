package com.project.bookbook.domain.entity;

import java.util.List;

import com.project.bookbook.domain.entity.EXKeywordEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "vv_intention")
public class VVIntentionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vv_no")
    private int vvNo;

    @Column(nullable = false)
    private String vvIntent; // 의도의 이름

    @OneToMany(mappedBy = "vvIntention")
    private List<EXKeywordEntity> keywords; // 키워드 리스트
}