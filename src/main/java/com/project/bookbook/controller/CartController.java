package com.project.bookbook.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.bookbook.domain.dto.PaymentAllDTO;
import com.project.bookbook.domain.dto.PaymentPostDTO;
import com.project.bookbook.domain.dto.UpdateCartDTO;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.CartService;
import com.project.bookbook.service.CouponService;
import com.project.bookbook.service.MypageUserService;
import com.project.bookbook.service.OrderService;

import groovy.util.logging.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {

	private final CouponService couponService;
	private final CartService cartService;
	private final OrderService orderService;
	private final MypageUserService userService;
	
	//장바구니
	@GetMapping("/cart")
	public String cartList(Model model, @AuthenticationPrincipal CustomUserDetails user) {
		cartService.findAllProcess(model, user);
		return "views/cart/cart-list";
	}
	
	@ResponseBody
	@DeleteMapping("/cart/{cartDetailNum}")
	public ResponseEntity<?> deleteCartItem(@PathVariable("cartDetailNum") Long cartDetailNum) {
		try {
			cartService.deleteCartDetail(cartDetailNum); //장바구니에 담긴 도서 개별 삭제
			ResponseEntity<?> response = ResponseEntity.ok().build();
			return response;
		} catch (Exception e) {
			ResponseEntity<?> response = ResponseEntity.internalServerError().body("삭제 과정에서 오류가 발생했습니다.");
			return response;
		}
	}
	
	@ResponseBody
	@PostMapping("/cart/all")
	public ResponseEntity<Object> paymentAll(@RequestBody PaymentAllDTO request, @AuthenticationPrincipal CustomUserDetails user) {
		if(request.getCartDetailNums().isEmpty()) {
			return ResponseEntity.badRequest().body("장바구니가 비어있습니다.");
			
		}else {
			List<Long> cartDetailNums = request.getCartDetailNums();
			long merchantUid = orderService.createOrder(cartDetailNums, user);
			return ResponseEntity.ok(merchantUid); //long형 orderNum을 직접 반환
		}
	}
	
	/*
	@PutMapping("/cart/update")
	@ResponseBody
    public String updateCartQuantity(@RequestBody UpdateCartDTO dto) {
		System.out.println(dto);
        try {
            cartService.updateCartItemQuantity(dto);
            System.out.println("업데이트 >>>>> ");
            return "장바구니 수량이 성공적으로 업데이트 되었습니다.";
        } catch (Exception e) {
            return "오류가 발생했습니다. : " + e.getMessage();
        }
    }
    */
	
	@PutMapping("/cart/update")
	@ResponseBody
    public ResponseEntity<String> updateCartQuantity(@RequestBody UpdateCartDTO dto) {
        //log.info("Received update request: {}", dto);
        try {
            cartService.updateCartItemQuantity(dto);
            //log.info("Cart updated successfully for cartDetailNum: {}", dto.getCartDetailNum());
            return ResponseEntity.ok("장바구니 수량이 성공적으로 업데이트 되었습니다.");
        } catch (Exception e) {
            //log.error("Error occurred while updating cart", e);
            return ResponseEntity.internalServerError().body("오류가 발생했습니다: " + e.getMessage());
        }
    }
	
	//결제
	@GetMapping("/payment/{merchantUid}")
	public String payment(@PathVariable("merchantUid") long merchantUid, Model model, @AuthenticationPrincipal CustomUserDetails user) {
		userService.readProcess(model, user);
		orderService.findOrdersInfo(model, merchantUid);
		couponService.findProcess(model, user);
		return "views/cart/payment";
	}
	
	@PostMapping("/payment/completion")
	@ResponseBody
	public String paymentPost(@RequestBody PaymentPostDTO dto, @AuthenticationPrincipal CustomUserDetails user) {
		dto.setPaidAmount(dto.getAmount() - dto.getCouponRate()); //쿠폰 할인 뺀 값으로 최종 값 변경
		try {
			orderService.orderCompletion(dto);
			cartService.cartEmptyProcess(user); //장바구니 비우기
			
			if(dto.getCouponNum() !=0 ) {
				couponService.couponStatusChange(dto.getCouponNum(), user); //쿠폰 사용 처리
			}
			
		}catch (Exception e) {
		}
		return "";
	}

	@GetMapping("/payment/completion/{merchantUid}")
	public String paymentCompletion(@PathVariable("merchantUid") long merchantUid, Model model) {
		orderService.findByMerchantUid(merchantUid, model);
		return "views/cart/completion";
	}

}