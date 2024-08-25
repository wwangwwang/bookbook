package com.project.bookbook.domain.dto.bot;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private String answer;
    private int vvNo;
    private int nnpNo;
    public AnswerDTO nnpNo(int nnpNo) {
    	this.nnpNo=nnpNo;
    	return this;
    }
    public AnswerDTO vvNo(int vvNo) {
    	this.vvNo=vvNo;
    	return this;
    }
}
