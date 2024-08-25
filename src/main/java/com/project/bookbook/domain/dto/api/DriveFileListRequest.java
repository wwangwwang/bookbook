package com.project.bookbook.domain.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriveFileListRequest {
    private String userId;
    private String fileId;
    private String orderBy;
    private int count;
    private String cursor;
}