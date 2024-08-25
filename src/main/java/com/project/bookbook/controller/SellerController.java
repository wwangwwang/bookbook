package com.project.bookbook.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.bookbook.domain.dto.OrderUpdateDTO;
import com.project.bookbook.domain.dto.RequestRegistDTO;
import com.project.bookbook.domain.dto.SellerUpdateDTO;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.MypageSellerService;
import com.project.bookbook.service.RegistService;
import com.project.bookbook.service.SellerIndexService;
import com.project.bookbook.service.SellerInventoryService;
import com.project.bookbook.service.SellerOrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class SellerController {

    private final RegistService registService;
    private final MypageSellerService mypageSellerService;
    private final SellerInventoryService sellerInventoryService;
    private final SellerOrderService sellerOrderService;
    private final SellerIndexService sellerIndexService;
    
    
    @GetMapping("/seller")
    public String seller(Model model) {
    	sellerIndexService.find(model);
        return "views/seller/index";
    }

    //상품
    @GetMapping("/seller/inventory")
    public String sellerInventory(Model model, @AuthenticationPrincipal CustomUserDetails seller) {
    	sellerInventoryService.findBook(model, seller);
        return "views/seller/inventory";
    }

    @GetMapping("/seller/inventory/write")
    public String sellerInventoryWrite() {
        return "views/seller/inventory-write";
    }

    @PostMapping("/seller/inventory/fileupload")
    @ResponseBody
    public Map<String, String> imgUpload(@RequestParam(value = "bookImgInput", required = false) MultipartFile bookImg) throws IOException {
    	
    	System.out.println(">>>???");
        if (bookImg == null || bookImg.isEmpty()) {
            return Map.of("error", "파일이 첨부되지 않았습니다.");
        }

        return registService.uploadFileToS3(bookImg);
    }


    @PostMapping("/seller/inventory/request")
    public String requestRegist(RequestRegistDTO requestRegistDTO) {
       
    	registService.saveProcess(requestRegistDTO);
       
        return "redirect:/seller";
    }
    
    @DeleteMapping("/seller/inventory/{bookNum}")
	public String deleteInventory(@PathVariable("bookNum") long bookNum) {
    	sellerInventoryService.deleteProcess(bookNum);
		return "redirect:/seller/inventory";
	}


    
    //주문
    @GetMapping("/seller/order")
    public String sellerOrder(Model model, @AuthenticationPrincipal CustomUserDetails seller) {
    	sellerOrderService.findBook(model, seller);
        return "views/seller/order";
    }
    @PutMapping("/seller/order/{merchantUid}")
    public String orderUpdate(@PathVariable("merchantUid") long merchantUid) {
    	sellerOrderService.updateProcess(merchantUid);
        return "redirect:/seller/order";
    }
    

    @GetMapping("/seller/order/exchange")
    public String sellerExchange() {
        return "views/seller/exchange";
    }

    @GetMapping("/seller/order/refund")
    public String sellerRefund() {
        return "views/seller/refund";
    }
    
    

   //리뷰

    @GetMapping("/seller/review")
    public String sellerReview() {
        return "views/seller/review";
    }

    //고객관리
    @GetMapping("/seller/user")
    public String sellerUser() {
        return "views/seller/users";
    }

    @GetMapping("/seller/user/detail")
    public String sellerDetail() {
        return "views/seller/users-detail";
    }
    
    //마이페이지
    @GetMapping("/seller/mypage")
	public String sellerMypage(Model model, @AuthenticationPrincipal CustomUserDetails seller) {
		mypageSellerService.readProcess(model,seller);
		return "views/seller/mypage";
	}
    
    @PutMapping("/seller/mypage")
	public String sellerUpdate(SellerUpdateDTO dto) {
		mypageSellerService.updateProcess(dto);
		return "redirect:/seller/mypage";
	}
}
