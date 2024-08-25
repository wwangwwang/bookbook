package com.project.bookbook.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.bookbook.domain.entity.SellerEntity;
import com.project.bookbook.service.SellerService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminApprovalController {
	
	 private final SellerService sellerService;
	 
	 //승인 대기 판매자 조회
	 @GetMapping("/admin/approve")
	    public String getPendingSellers(Model model) {
	        List<SellerEntity> pendingSellers = sellerService.getPendingSellers();
	        model.addAttribute("pendingSellers", pendingSellers);
	        return "views/login/admin-sign";
	    }
	 	
	 //출판사 승인
	    @PostMapping("/approve-seller/{sellerId}")
	    public String approveSeller(@PathVariable("sellerId") Long sellerId, RedirectAttributes redirectAttributes) {
	        try {
	            sellerService.approveSeller(sellerId);
	            redirectAttributes.addFlashAttribute("message", "출판사가 승인되었습니다.");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "승인 중 오류가 발생했습니다: " + e.getMessage());
	        }
	        return "redirect:/admin/approve";
	    }
	    
	    //출판사 거부
	    @PostMapping("/reject-seller/{sellerId}")
	    public String rejectSeller(@PathVariable("sellerId") Long sellerId, RedirectAttributes redirectAttributes) {
	        try {
	            sellerService.rejectSeller(sellerId);
	            redirectAttributes.addFlashAttribute("message", "출판사가 거부되었습니다.");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "거부 중 오류가 발생했습니다: " + e.getMessage());
	        }
	        return "redirect:/admin/approve";
	    }
	    
	    //모든 판매자 조회후 shopName으로 검색 
	    @GetMapping("/admin/sellers")
	    public String listSellers(@RequestParam(name = "shopName", required = false) String shopName, Model model) {
	        List<SellerEntity> sellers;
	        if (shopName != null && !shopName.isEmpty()) {
	            sellers = sellerService.findSellersByShopName(shopName);
	        } else {
	            sellers = sellerService.getAllSellers();
	        }
	        model.addAttribute("sellers", sellers);
	        return "views/admin/sellers";
	    }
	    
	    //판매자 상세 정보 조회
	    @GetMapping("/admin/sellers/detail/{id}")
	    public String adminSellerDetail(@PathVariable("id") Long id, Model model) {
	        SellerEntity seller = sellerService.getSellerById(id);
	        model.addAttribute("seller", seller);
	        return "views/admin/sellers-detail";
	    }
	    
	    // 판매자 삭제
	    @PostMapping("/admin/sellers/delete/{id}")
	    public String deleteSeller(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
	        try {
	            sellerService.deleteSeller(id);
	            redirectAttributes.addFlashAttribute("message", "출판사 계정이 성공적으로 삭제되었습니다.");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "계정 삭제 중 오류가 발생했습니다: " + e.getMessage());
	        }
	        return "redirect:/admin/sellers";
	    }
	    
	       
	}