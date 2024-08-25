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
public class Files {
	 private String accessedTime;
	    private String createdTime;
	    private String fileId;
	    private String parentFileId;
	    private String fileName;
	    private long fileSize;
	    private String filePath;
	    private String fileType;
	    private boolean hasPermission;
	    private String permissionRootFileId;
	    private boolean shared;
	    private String shareRootFileId;
	    private int resourceLocation;
	    private List<FileStatus> statuses;
	    private String modifiedTime;
	}