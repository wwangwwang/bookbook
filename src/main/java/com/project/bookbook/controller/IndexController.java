package com.project.bookbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.bookbook.domain.dto.BookDTO;
import com.project.bookbook.domain.dto.ReviewDTO;
import com.project.bookbook.domain.dto.BookSearchResponse.Item;
import com.project.bookbook.domain.dto.CouponListDTO;
import com.project.bookbook.domain.entity.UserCouponEntity;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.security.CustomUserDetails;
import com.project.bookbook.service.BookService;
import com.project.bookbook.service.ReviewService;
import com.project.bookbook.service.UserCouponService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

	// 책 서비스
	@Autowired
	private final BookService bookservice;
	private final ReviewService reviewService;
	private final UserCouponService userCouponService;

	// 메인페이지 이동
	@GetMapping
	public String main(Model model) {
		bookservice.getDefaultBooks(model);
		return "views/index/index.html";
	}
	//신상품
	@GetMapping("/newbookList")
	public String newListBooks(Model model) {
		bookservice.getNewBook(model);
		return "views/index/newBookList";
	}
	
	@GetMapping("/newDetail/{isbn}")
	public String newdetail(@PathVariable("isbn") String isbn, Model model) {
		System.out.println(">>>>>>>>>>>>> "+isbn);
		bookservice.getNewIsbn(isbn,model);
		return "views/index/newDetail";
		
	}
	
	//도서 정보
	@GetMapping("/bookList")
	public String listBooks(Model model) {
		System.out.println(">>>>>>>>>>>>" + model);
		bookservice.getBookList(model);
		return "views/index/serchBookList";
	}
	//검색 정보
	@GetMapping("/search")
	public String search(@RequestParam("query") String query, Model model) {
		bookservice.searchBooks(query, model);
		return "views/index/serchBookList.html";
	}

	// 상세 도서 정보
	@GetMapping("/detail/{isbn}")
	public String detail(@PathVariable("isbn") String isbn, Model model) {
		try {
			Item book = bookservice.getBookByIsbn(isbn);
			if (book != null) {
				model.addAttribute("book", book);
				List<ReviewDTO> reviews = reviewService.getReviewsByIsbn(isbn);
				model.addAttribute("reviews", reviews);
			} else {
				model.addAttribute("error", "도서를 찾을 수 없습니다.");
			}
		} catch (Exception e) {
			model.addAttribute("error", "도서 정보를 가져오는 중 오류가 발생했습니다.");
		}
		return "views/index/detail";
	}

	/**
	 * 새로운 리뷰를 생성합니다.
	 * 
	 * @param bookNum     리뷰 대상 책의 번호
	 * @param content     리뷰 내용
	 * @param rate        평점
	 * @param userDetails 현재 로그인한 사용자의 정보
	 * @return 생성된 리뷰 정보를 담은 ResponseEntity
	 */
	@PostMapping("/detail/{isbn}/review")
	@ResponseBody
	public ResponseEntity<ReviewDTO> addReview(@PathVariable(name = "isbn") String isbn,
			@RequestBody ReviewCreateRequest reviewRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
		try {
			ReviewDTO newReview = reviewService.createReview(userDetails.getUserId(), isbn,
					reviewRequest.getReviewContent(), reviewRequest.getRate());
			return ResponseEntity.ok(newReview);
		} catch (Exception e) {
			System.err.println("Error creating review: " + e.getMessage()); // 로그 추가
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// 즐겨찾기 데이터 담기
	@PostMapping("/api/books/wish")
	@ResponseBody
	public ResponseEntity<String> addToWishlist(@RequestParam("isbn") String isbn,
			@AuthenticationPrincipal CustomUserDetails userDetails) {

		// System.out.println("요청 받음 - ISBN: " + isbn + ", 사용자: " + userDetails);

		try {
			Long userId = userDetails.getUserId();
			// System.out.println("처리 중 - ISBN: " + isbn + ", 사용자 ID: " + userId);

			bookservice.addToWishlist(isbn, userId);

			return ResponseEntity.ok("Book successfully added to wishlist///");
		} catch (Exception e) {
			// System.err.println("위시리스트 추가 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error adding book to wishlist: ///" + e.getMessage());
		}
	}

	// 장바구니 담기

	@PostMapping("/api/books/cartItem")
	@ResponseBody
	public ResponseEntity<String> addToCart(@RequestParam("isbn") String isbn,
			@RequestParam(value = "quantity", defaultValue = "1") int quantity,
			@AuthenticationPrincipal CustomUserDetails userDetails) {

		// System.out.println("요청 받음 - ISBN: " + isbn + ", 수량: " + quantity + ", 사용자: "
		// + userDetails);

		try {
			Long userId = userDetails.getUserId();
			System.out.println("처리 중 - ISBN: " + isbn + ", 수량: " + quantity + ", 사용자 ID: " + userId);

			bookservice.addToCart(isbn, userId, quantity);

			return ResponseEntity.ok("Book successfully added to cart");
		} catch (Exception e) {
			// System.err.println("장바구니 추가 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error adding book to cart: " + e.getMessage());
		}
	}

	// 이벤트페이지
	@GetMapping("/event")
	public String event(Model model) {
		userCouponService.list(model);
		return "views/index/event";
	}

	// 당첨된 쿠폰 조회해서 불러오기
	@GetMapping("/api/coupons/{id}")
	@ResponseBody
	public ResponseEntity<CouponListDTO> getCouponById(@PathVariable("id") String id) {
		CouponListDTO coupon = userCouponService.getCouponById(id);
		if (coupon != null) {
			return ResponseEntity.ok(coupon);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// 사용자에게 쿠폰 전달하기
	@PostMapping("/api/save-coupon")
	@ResponseBody
	public ResponseEntity<UserCouponEntity> saveCoupon(@RequestBody SaveCouponRequest request,
			@AuthenticationPrincipal CustomUserDetails userDetails) {

		if (userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		System.out.println("쿠폰 할당 요청 받음 - 쿠폰 ID: " + request.getCouponId() + ", 사용자 ID: " + userDetails.getUserId());

		try {
			UserCouponEntity userCoupon = userCouponService.assignCouponToUser(request.getCouponId(),
					userDetails.getUserId());
			return ResponseEntity.ok(userCoupon);
		} catch (RuntimeException e) {
			System.err.println("쿠폰 할당 중 오류 발생: " + e.getMessage());
			return ResponseEntity.badRequest().body(null);
		}
	}

}