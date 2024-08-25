package com.project.bookbook.domain.dto.api;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriveFileListResponseDTO {
	 private List<File> files;
	 private ResponseMetadata responseMetadata;
}