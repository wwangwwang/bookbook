package com.project.bookbook.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.bookbook.domain.dto.CombinedSellerDTO;
import com.project.bookbook.domain.dto.UserSaveDTO;
import com.project.bookbook.domain.entity.ApprovalStatus;
import com.project.bookbook.domain.entity.ImageEntity;
import com.project.bookbook.domain.entity.Role;
import com.project.bookbook.service.ImageService;
import com.project.bookbook.service.SellerService;
import com.project.bookbook.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@SessionAttributes("combinedSellerDTO")
public class SignupController {

	private final UserService userService;
	private final SellerService sellerService;
	private final ImageService imageService;

	@ModelAttribute("combinedSellerDTO")
	public CombinedSellerDTO combinedSellerDTO() {
		return new CombinedSellerDTO();
	}

	@GetMapping("/signup")
	public String signup() {
		return "views/login/signup";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute UserSaveDTO dto, RedirectAttributes redirectAttributes) {
		if (userService.isEmailDuplicate(dto.getEmail())) {
			redirectAttributes.addFlashAttribute("errorMessage", "이미 사용 중인 이메일입니다.");
			return "redirect:/signup";
		}

		try {
			userService.signupProcess(dto, Role.USER);
			return "redirect:/login"; // 회원가입 성공 시 리다이렉트할 경로
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "회원가입 중 오류가 발생했습니다: " + e.getMessage());
			return "redirect:/signup";
		}
	}

	@PostMapping("/signup/save-and-redirect")
	@ResponseBody
	public ResponseEntity<String> saveAndRedirect(@ModelAttribute("combinedSellerDTO") CombinedSellerDTO dto) {
		try {
			// dto를 세션에 저장합니다. @SessionAttributes 어노테이션이 이를 처리합니다.
			// 필요한 경우 여기에 추가적인 로직을 구현할 수 있습니다.
			return ResponseEntity.ok("success");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("error");
		}
	}

	@GetMapping("/signup/admin")
	public String signupAdmin(@ModelAttribute("combinedSellerDTO") CombinedSellerDTO dto, Model model) {
		model.addAttribute("combinedSellerDTO", dto);
		return "views/login/signup-admin";
	}
	
	@PostMapping("/signup/admin")
	public String signupAdmin(@ModelAttribute("combinedSellerDTO") CombinedSellerDTO dto, SessionStatus status,
			RedirectAttributes redirectAttributes) {
		if (sellerService.isEmailDuplicate(dto.getEmail())) {
			redirectAttributes.addFlashAttribute("errorMessage", "이미 사용 중인 이메일입니다.");
			return "redirect:/signup/admin";
		}

		if (sellerService.isBusinessNumDuplicate(dto.getBusinessNum())) {
			redirectAttributes.addFlashAttribute("errorMessage", "이미 등록된 사업자 번호입니다.");
			return "redirect:/signup/admin";
		}

		try {
			dto.setApprovalStatus(ApprovalStatus.PENDING);
			sellerService.signupProcess(dto);
			status.setComplete();
			return "redirect:/approve";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "회원가입 중 오류가 발생했습니다: " + e.getMessage());
			return "redirect:/signup/admin";
		}
	}

	@GetMapping("/approve")
	public String approve() {
		return "/views/login/approve";
	}

	@PostMapping("/api/upload")
	@ResponseBody
	public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file,
			@ModelAttribute("combinedSellerDTO") CombinedSellerDTO dto) {
		
		try {
			// 파일이 비어있는지 확인
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
			}
			 // 파일 타입이 PNG인지 확인
			if (!file.getContentType().equals("image/png")) {
				return ResponseEntity.badRequest().body(Map.of("error", "Only PNG files are allowed"));
			}
			// 이미지 서비스를 통해 s3 파일 업로드 및 저장
			ImageEntity savedImage = imageService.uploadImage(file);
			
			dto.setBusinessRegImageId(savedImage.getId());
			dto.setBusinessReg(savedImage.getFileUrl());
			
			return ResponseEntity.ok(Map.of("id", savedImage.getId().toString(), "url", savedImage.getFileUrl()));
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("error", "File upload failed: " + e.getMessage()));
		}
	}
}
