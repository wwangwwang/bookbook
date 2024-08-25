package com.project.bookbook.domain.dto.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriveFileListResponse {
    private List<Files> files;
    private ResponseMetadata responseMetaData;
}