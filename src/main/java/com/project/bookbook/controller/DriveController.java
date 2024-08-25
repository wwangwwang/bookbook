package com.project.bookbook.controller;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.project.bookbook.domain.dto.api.File;
import com.project.bookbook.domain.dto.api.Files;
import com.project.bookbook.service.api.DriveService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DriveController {
	  
	    private final DriveService driveservice;
	    

		@Value("${naver.client.id}")
		private String clientId;
		
		@Value("${naver.client.secret}")
		private String clientSecret;
		
		@Value("${naver.client.scope}")
		private String clientScope;
		
		@Value("${naver.client.redirect-uri}")
		private String redirectUri;
		
		// OAuth2 인증 후 리다이렉트되는 엔드포인트
		@GetMapping("/oauth2/code")
	    public String redirectUri(@RequestParam("code") String code,
	                              @RequestParam("state") String state,
	                              Model model,
	                              HttpSession session) throws Exception {
	        if (state.equals("drive_access")) {
	        	 // 인증 코드로 액세스 토큰 얻기
	            String accessToken = driveservice.getAccessTokenForCode(code);
	            // 세션에 액세스 토큰 저장
	            session.setAttribute("accessToken", accessToken);
	            System.out.println("AccessToken stored in session: " + accessToken);
	            // 루트 파일 목록 조회
	            List<Files> rootfiles = driveservice.rootfileRead(accessToken, model);
	            model.addAttribute("rootfiles", rootfiles);
	            return "views/naver/root-drive";
	        }
	        return "redirect:/";
	    }
	    
		// 루트 폴더의 파일 목록을 조회하는 GET 엔드포인트		
	    @GetMapping("/admin/drive/files")
	    public String listFiles(@RequestParam("fileId") String fileId,
	                            Model model,
	                            HttpSession session) throws Exception {
	        String accessToken = (String) session.getAttribute("accessToken");
	        System.out.println("AccessToken retrieved from session: " + accessToken);
	        if (accessToken == null) {
	            return "redirect:/drive/auth";
	        }
	        List<File> fileList = driveservice.fileRead(accessToken, fileId, model);
	        
	        // 디버깅을 위한 로그 추가
	        System.out.println("File List:");
	        for (File file : fileList) {
	            System.out.println(file);
	        }
	        
	        model.addAttribute("files", fileList);
	        model.addAttribute("parentFileId", fileId);
	        return "views/naver/drive";
	    }

	    // 특정 폴더의 파일 목록을 조회하는 POST 엔드포인트 (GET과 유사)
	    @PostMapping("/admin/drive/files")
	    public String listFilesPost(@RequestParam("fileId") String fileId,
	                                Model model,
	                                HttpSession session) throws Exception {
	        // 세션에서 액세스 토큰을 가져오는 부분 추가
	        String accessToken = (String) session.getAttribute("accessToken");
	        // 추가된 로그
	        System.out.println("AccessToken retrieved from session (POST): " + accessToken);
	        if (accessToken == null) {
	            return "redirect:/drive/auth";
	        }
	        
	        List<File> fileList = driveservice.fileRead(accessToken, fileId, model);
	        model.addAttribute("files", fileList);
	        model.addAttribute("parentFileId", fileId);
	        return "views/naver/drive";
	    }

	    // 파일 다운로드 엔드포인트	    
	    @GetMapping("/admin/drive/files/download")
	    public ResponseEntity<Resource> downloadFile(@RequestParam("fileId") String fileId, HttpSession session) {
	        String accessToken = (String) session.getAttribute("accessToken");
	        if (accessToken == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	        }

	        try {
	        	 // 파일 이름 조회
	            String fileName = driveservice.getFileName(accessToken, fileId);
	            // 파일 다운로드 URL 조회
	            String downloadUrl = driveservice.getFileDownloadUrl(accessToken, fileId);
	            // 파일 데이터 다운로드
	            byte[] fileData = driveservice.downloadFileFromUrl(accessToken, downloadUrl);

	            ByteArrayResource resource = new ByteArrayResource(fileData);
	            
	            // 파일 다운로드 응답 생성
	            return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + "\"")
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .contentLength(fileData.length)
	                .body(resource);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ByteArrayResource(("Failed to download file: " + e.getMessage()).getBytes()));
	        }
	    }
	    
	    
	    // 사용자 정보를 처리하는 엔드포인트
		@PostMapping("/userinfo")
		@ResponseBody
		public Map<String, Object> handleUserInfo(@RequestBody Map<String, Object> userInfo) {
	        // 사용자 정보를 처리하는 로직 (예: 사용자 세션 생성, 데이터베이스 저장 등)
	        System.out.println("Received user info: " + userInfo);
	        
	        // 사용자 정보 처리 후 응답 반환
	        return Map.of("status", "success", "message", "User info processed successfully");
	    }
	}