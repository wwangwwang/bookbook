package com.project.bookbook.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class ItemAndFileSaveDTO {
	
	private List<String> bucketkey;//이미지 버킷키
	private List<String> orgName;//이미지 이름
	
}
