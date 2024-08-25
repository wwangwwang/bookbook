package com.project.bookbook.domain.dto.bot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO {
	private String content; // 응답 메시지 내용
    private Set<String> nouns; // 명사 집합
    private Set<String> verbs; // 동사 집합 (추가된 필드)
}