package com.project.bookbook.service.api;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.bookbook.domain.api.OpenApiUtil;
import com.project.bookbook.domain.dto.api.DriveFileListResponse;
import com.project.bookbook.domain.dto.api.DriveFileListResponseDTO;
import com.project.bookbook.domain.dto.api.File;
import com.project.bookbook.domain.dto.api.Files;
import com.project.bookbook.domain.dto.api.NaverTokenDTO;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class DriveService {
	
	private final OpenApiUtil openApiUtil;
	
	@Value("${naver.client.id}")
	private String clientId;
	
	@Value("${naver.client.secret}")
	private String clientSecret;
	
	@Value("${naver.client.domain}")
	private String domainId;
	
	// 인증 코드로 액세스 토큰을 얻는 메서드
	public String getAccessTokenForCode(String code) throws Exception {
        NaverTokenDTO tokenResult = getAccessToken(code);
        return tokenResult.getAccess_token();
    }
	
	// 루트 폴더의 파일 목록을 읽는 메서드
	public List<Files> rootfileRead(String accessToken, Model model) throws Exception {
        System.out.println("Access Token: " + accessToken);
        
        String apiUrl = "https://www.worksapis.com/v1.0/users/me/drive/files";
        
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        
        String fileResponseResult = openApiUtil.request(apiUrl, headers, "GET", null);
        System.out.println("fileResponseResult: " + fileResponseResult);
        
        DriveFileListResponse resultFiles = openApiUtil.objectMapper(fileResponseResult, new TypeReference<DriveFileListResponse>() {});
        List<Files> rootfiles = resultFiles.getFiles();

        model.addAttribute("rootfiles", rootfiles);
        model.addAttribute("metadata", resultFiles.getResponseMetaData());

        return rootfiles;
    }
	
	// 루트 폴더의 파일 목록을 읽는 메서드
    public List<File> fileRead(String accessToken, String fileId, Model model) throws Exception {
        System.out.println(accessToken);

        String apiUrl = "https://www.worksapis.com/v1.0/users/me/drive/files/" + fileId + "/children";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        String fileResponseResult = openApiUtil.request(apiUrl, headers, "GET", null);
        System.out.println("fileResponseResult: " + fileResponseResult);
        
        DriveFileListResponseDTO resultFiles = openApiUtil.objectMapper(fileResponseResult, new TypeReference<DriveFileListResponseDTO>() {});
        List<File> files = resultFiles.getFiles();

        model.addAttribute("files", files);
        model.addAttribute("metadata", resultFiles.getResponseMetadata());

        return files;
    }

    // 인증 코드로 액세스 토큰을 얻는 내부 메서드
    private NaverTokenDTO getAccessToken(String code) throws Exception {
        String apiUrl = "https://auth.worksmobile.com/oauth2/v2.0/token";

        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        String responseBody = openApiUtil.request(apiUrl, headers, "POST", encodeParameters(params));
        return openApiUtil.objectMapper(responseBody, new TypeReference<NaverTokenDTO>() {});
    }
    
    // 파일 이름을 얻는 메서드
    public String getFileName(String accessToken, String fileId) throws Exception {
        String apiUrl = "https://www.worksapis.com/v1.0/users/me/drive/files/" + fileId;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        try {
            String responseBody = openApiUtil.request(apiUrl, headers, "GET", null);
            Map<String, Object> fileInfo = openApiUtil.objectMapper(responseBody, new TypeReference<Map<String, Object>>() {});

            String fileName = (String) fileInfo.get("fileName");
            if (fileName == null) {
                throw new IllegalStateException("File name not found in API response");
            }
            return fileName;
        } catch (Exception e) {
            throw e;
        }
    }
    
    // 파일 다운로드 URL을 얻는 메서드
    public String getFileDownloadUrl(String accessToken, String fileId) throws Exception {
        String apiUrl = "https://www.worksapis.com/v1.0/users/me/drive/files/" + fileId + "/download";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        try {
            return openApiUtil.requestForRedirect(apiUrl, headers, "GET", null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get file download URL", e);
        }
    }
    
    // 실제 파일을 다운로드하는 메서드
    public byte[] downloadFileFromUrl(String accessToken, String downloadUrl) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Content-Type", "application/octet-stream");

        try {
            byte[] fileData = openApiUtil.requestBinary(downloadUrl, headers, "GET", null);
            if (fileData == null || fileData.length == 0) {
                throw new RuntimeException("Downloaded file is empty");
            }
            return fileData;
        } catch (Exception e) {
            
            throw new RuntimeException("Failed to download file", e);
        }
    }
    
    // URL 인코딩을 위한 유틸리티 메서드
    private String encodeParameters(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
            result.append("&");
        }
        return result.toString();
    }
}