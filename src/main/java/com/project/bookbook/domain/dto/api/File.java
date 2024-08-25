package com.project.bookbook.domain.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class File {
    private String fileId;
    private String parentFileId;
    private int resourceLocation;
    private long fileSize;
    private String fileName;
    private String filePath;
    private FileType fileType;
    private String createdTime;
    private String modifiedTime;
    private String accessedTime;
    private boolean hasPermission;
    private String permissionRootFileId;
    private boolean shared;
    private String shareRootFileId;
    private String imageUrl; // 이미지 파일의 URL을 저장할 필드

    // 만약 이미지 파일인 경우 URL을 설정하기 위한 메서드 추가
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}