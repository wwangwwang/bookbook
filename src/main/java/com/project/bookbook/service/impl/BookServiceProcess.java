package com.project.bookbook.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.bookbook.domain.dto.BookDTO;
import com.project.bookbook.domain.dto.BookSearchResponse;
import com.project.bookbook.domain.dto.BookSlide;
import com.project.bookbook.domain.dto.NaverBookItem;
import com.project.bookbook.domain.dto.NaverBookResponse;
import com.project.bookbook.domain.dto.ReviewDTO;
import com.project.bookbook.domain.dto.BookSearchResponse.Item;
import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.entity.CartDetailEntity;
import com.project.bookbook.domain.entity.CartEntity;
import com.project.bookbook.domain.entity.FavoriteBook;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.entity.WishEntity;
import com.project.bookbook.domain.repository.BookRepository;
import com.project.bookbook.domain.repository.FavoriteBookRepository;
import com.project.bookbook.domain.repository.UserRepository;
import com.project.bookbook.domain.repository.WishRepository;
import com.project.bookbook.mapper.BookMapper;
import com.project.bookbook.service.BookService;
import com.project.bookbook.service.CartItemService;
import com.project.bookbook.service.CartService;
import com.project.bookbook.service.ReviewService;

import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceProcess implements BookService {

	@Value("${naver.openapi.clientId}")
	private String clientId;

	@Value("${naver.openapi.clientSecret}")
	private String clientSecret;

	private static final String NAVER_BOOK_API_URL = "https://openapi.naver.com/v1/search/book.json";

	private final FavoriteBookRepository favoriteBookRepository;
	private final BookRepository bookRepository;
	private final CartItemService cartItemService;
	private final UserRepository userRepository;
	private final WishRepository wishRepository;
	private final BookMapper bookmapper;
	private final ReviewService reviewService;

	// 검색 결과
	public void searchBooks(String query, Model model) {
		try {
			String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
			String urlString = NAVER_BOOK_API_URL + "?query=" + encodedQuery + "&display=20";
			URL url = new URL(urlString);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("X-Naver-Client-Id", clientId);
			conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
				String inputLine;
				StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				ObjectMapper mapper = new ObjectMapper();
				NaverBookResponse bookResponse = mapper.readValue(response.toString(), NaverBookResponse.class);

				if (bookResponse != null && bookResponse.getItems() != null && !bookResponse.getItems().isEmpty()) {
					List<BookDTO> books = bookResponse.getItems().stream().map(this::convertToBookDTO)
							.collect(Collectors.toList());
					model.addAttribute("books", books);
					model.addAttribute("query", query);
					logger.info("검색 결과: {} 건", books.size());
				} else {
					model.addAttribute("books", Collections.emptyList());
					model.addAttribute("error", "검색 결과가 없습니다.");
					logger.info("검색 결과 없음: {}", query);
				}
			} else {
				logger.error("HTTP 요청 실패: {}", responseCode);
				model.addAttribute("books", Collections.emptyList());
				model.addAttribute("error", "API 요청 실패: " + responseCode);
			}
		} catch (Exception e) {
			logger.error("책 검색 중 오류 발생: {}", e.getMessage(), e);
			model.addAttribute("books", Collections.emptyList());
			model.addAttribute("error", "검색 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	private BookDTO convertToBookDTO(NaverBookItem item) {
		BookDTO book = new BookDTO();
		book.setTitle(item.getTitle() != null ? item.getTitle() : "");
		book.setAuthor(item.getAuthor() != null ? item.getAuthor() : "");
		book.setPublisher(item.getPublisher() != null ? item.getPublisher() : "");
		book.setPubdate(item.getPubdate() != null ? item.getPubdate() : "");
		book.setDescription(truncateDescription(item.getDescription()));
		book.setIsbn(item.getIsbn() != null ? item.getIsbn() : "");
		book.setImage(item.getImage() != null ? item.getImage() : "");
		book.setLink(item.getLink() != null ? item.getLink() : "");
		book.setDiscount(item.getDiscount() != null ? item.getDiscount() : "");
		return book;
	}

	// 도서 리스트 뿌리기
	@Override
	public void getBookList(Model model) {
		// 1. "만화"라는 쿼리로 책 정보를 가져오는 메소드 호출
		List<BookDTO> books = fetchBooksFromAPI("만화");

		// 2. 가져온 책 정보를 모델에 "books"라는 이름으로 추가
		model.addAttribute("books", books);
	}

	// fetchBooksFromAPI 메서드 수정 (BookDTO 리스트 반환)
	private List<BookDTO> fetchBooksFromAPI(String query) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", clientId);
		headers.set("X-Naver-Client-Secret", clientSecret);

		String url = NAVER_BOOK_API_URL + "?query=" + query + "&display=20"; // 20개의 결과를 가져옵니다.

		ResponseEntity<NaverBookResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<>(headers), NaverBookResponse.class);

		NaverBookResponse response = responseEntity.getBody();
		if (response != null && response.getItems() != null) {
			return response.getItems().stream().map(this::convertToBookDTO).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	private List<BookDTO> parseBookResults(Map<String, Object> responseBody) {
		// API 응답에서 "items"라는 키로 책 정보를 리스트 형태로 추출
		List<Map<String, Object>> items = (List<Map<String, Object>>) responseBody.get("items");
		List<BookDTO> books = new ArrayList<>();

		// 추출한 책 정보들을 순회하며 BookDTO 객체로 변환
		for (Map<String, Object> item : items) {
			// 새로운 BookDTO 객체 생성
			BookDTO book = new BookDTO();

			// 각 필드에 API 응답에서 추출한 값을 설정
			book.setTitle((String) item.get("title")); // 책 제목 설정
			book.setAuthor((String) item.get("author")); // 저자 설정
			book.setPublisher((String) item.get("publisher")); // 출판사 설정
			book.setPubdate((String) item.get("pubdate")); // 출판일 설정

			// 설명 텍스트를 잘라서 설정 (설명이 너무 길 경우를 대비)
			String fullDescription = (String) item.get("description");
			book.setDescription(truncateDescription(fullDescription));

			// ISBN 설정 (추후 파싱 로직 수정 가능)
			book.setIsbn((String) item.get("isbn"));

			// 이미지 URL 설정
			book.setImage((String) item.get("image"));

			// 상세 정보 링크 설정
			book.setLink((String) item.get("link"));

			// 할인된 가격 설정 (판매가)
			book.setDiscount((String) item.get("discount"));

			// 카테고리 정보가 있는 경우 설정
			if (item.containsKey("category")) {
				book.setCategory((String) item.get("category"));
			}

			// 변환된 BookDTO 객체를 리스트에 추가
			books.add(book);
		}

		// 변환된 BookDTO 리스트 반환
		return books;
	}

	private static final Logger logger = LoggerFactory.getLogger(BookServiceProcess.class);

	// 책소개 250자 내외로 보여줘
	private String truncateDescription(String description) {
		logger.debug("Original description: {}", description);

		if (description == null) {
			return "";
		}

		// 300자 이하면 그대로 반환
		if (description.length() <= 250) {
			return description;
		}

		// 300자까지 자르기
		String truncated = description.substring(0, 250);

		// 단어 중간에서 잘리는 것을 방지하기 위해 마지막 공백 이후로 자르기
		int lastSpace = truncated.lastIndexOf(' ');
		if (lastSpace > 0) {
			truncated = truncated.substring(0, lastSpace);
		}

		// 생략 부호 추가
		String result = truncated + "...";

		logger.debug("Truncated description: {}", result);
		return result;
	}

	@Override
	public Item getBookByIsbn(String isbn) {
		// System.out.println("Fetching book details for ISBN: " + isbn);
		String url = "https://openapi.naver.com/v1/search/book_adv.xml?d_isbn=" + isbn;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", clientId);
		headers.set("X-Naver-Client-Secret", clientSecret);
		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

			if (response.getStatusCode().is2xxSuccessful()) {
				String xmlResponse = response.getBody();

				JAXBContext jaxbContext = JAXBContext.newInstance(BookSearchResponse.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(xmlResponse);
				BookSearchResponse bookSearchResponse = (BookSearchResponse) unmarshaller.unmarshal(reader);

				if (!bookSearchResponse.getChannel().getItems().isEmpty()) {
					BookSearchResponse.Item item = bookSearchResponse.getChannel().getItems().get(0);

					// 평균 평점과 리뷰 수 계산
					double averageRating = reviewService.calculateAverageRating(isbn);
					int reviewCount = reviewService.getReviewCount(isbn);

					// Item 객체에 평균 평점과 리뷰 수 설정
					item.setAverageRating(averageRating);
					item.setReviewCount(reviewCount);

					return item;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void getDefaultBooks(Model model) {
		List<BookDTO> bestSellerBooks = fetchBooksFromAPI("베스트셀러");
		List<BookSlide> bestSellerSlides = createBookSlides(bestSellerBooks);
		model.addAttribute("bestSellerSlides", bestSellerSlides);

	}

	private List<BookSlide> createBookSlides(List<BookDTO> books) {
		List<BookSlide> slides = new ArrayList<>();
		for (int i = 0; i < books.size(); i += 4) {
			int end = Math.min(i + 4, books.size());
			slides.add(new BookSlide(books.subList(i, end)));
		}
		return slides;
	}

	@Override
	public void addToFavorites(String isbn) throws Exception {
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		// System.out.println("Adding book with ISBN " + isbn + " to favorites");
		try {
			Item bookDetail = getBookByIsbn(isbn);
			// System.out.println("Retrieved book details: " + bookDetail);

			if (bookDetail == null) {
				// System.out.println("Book details not found for ISBN: " + isbn);
				throw new Exception("Book not found");
			}

			FavoriteBook favoriteBook = convertToFavoriteBook(bookDetail);
			// System.out.println("Converted to FavoriteBook: " + favoriteBook);

			favoriteBookRepository.save(favoriteBook);
			// System.out.println("Book successfully added to favorites: " + isbn);
		} catch (Exception e) {
			// System.out.println("Error adding book to favorites: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	private FavoriteBook convertToFavoriteBook(Item item) {
		// System.out.println("Converting Item to FavoriteBook: " + item);
		return FavoriteBook.builder().isbn(item.getIsbn()).title(item.getTitle()).author(item.getAuthor())
				.publisher(item.getPublisher())
				// .publicationDate(parsePublicationDate(item.getPubdate()))
				.description(truncateDescription(item.getDescription())).imageUrl(item.getImage())
				.discountPrice(parseDiscountPrice(item.getDiscount())).build();
	}

	private Integer parseDiscountPrice(String discount) {
		// System.out.println("Parsing discount price: " + discount);
		try {
			return Integer.parseInt(discount);
		} catch (NumberFormatException e) {
			// System.out.println("Failed to parse discount price: " + discount);
			e.printStackTrace();
			return null; // 또는 다른 기본값 사용
		}
	}

	@Override
	@Transactional
	public void addToCart(String isbn, Long userId, int quantity) {
		System.out.println("서비스 시작 - ISBN: " + isbn + ", 사용자 ID: " + userId + ", 수량: " + quantity);

		BookEntity book = findOrCreateBook(isbn);

		try {
			cartItemService.addToCart(book, userId, quantity);
			System.out.println("장바구니에 책 추가 완료 - 수량: " + quantity);
		} catch (Exception e) {
			System.err.println("장바구니에 책 추가 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
			throw e; // 상위 레벨로 예외를 전파
		}
	}

	private BookEntity findOrCreateBook(String isbn) {
		return bookRepository.findByIsbn(isbn).orElseGet(() -> {
			System.out.println("책을 찾지 못함. 네이버 API에서 정보 가져오기 시도");
			Item naverBookItem = getBookByIsbn(isbn);
			if (naverBookItem == null) {
				throw new RuntimeException("Book not found in Naver API");
			}
			BookEntity newBook = createAndSaveBookEntity(naverBookItem);
			System.out.println("새 책 저장됨 - BookNum: " + newBook.getBookNum());
			return newBook;
		});
	}

	private BookEntity createAndSaveBookEntity(Item item) {
		BookEntity book = BookEntity.builder().bookName(item.getTitle()).bookImg(item.getImage())
				.author(item.getAuthor()).publisher(item.getPublisher())
				.description(truncateDescription(item.getDescription())).isbn(item.getIsbn())
				.discount(parseDiscountPrice(item.getDiscount())).link(item.getLink()).build();
		return bookRepository.save(book);
	}

	@Override
	public void addToWishlist(String isbn, Long userId) throws Exception {
		// 1. 사용자 조회
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));

		// 2. 책 정보 조회 또는 생성
		BookEntity book = bookRepository.findByIsbn(isbn).orElseGet(() -> {
			// 2.1 책이 데이터베이스에 없는 경우, 네이버 API에서 정보 가져오기
			Item naverBookItem = getBookByIsbn(isbn);
			if (naverBookItem == null) {
				throw new RuntimeException("네이버 API에서 책을 찾을 수 없습니다: " + isbn);
			}
			// 2.2 네이버 API에서 가져온 정보로 BookEntity 생성 및 저장
			return createAndSaveBookEntity(naverBookItem);
		});

		// 3. 위시리스트 중복 체크
		if (wishRepository.existsByUserAndBook(user, book)) {
			throw new Exception("이미 위시리스트에 추가된 책입니다.");
		}

		// 4. 위시리스트에 추가
		WishEntity wish = WishEntity.builder().user(user).book(book).build();
		wishRepository.save(wish);

		// 5. 로그 기록
		logger.info("책이 위시리스트에 추가되었습니다. ISBN: {}, 사용자 ID: {}", isbn, userId);
	}

	// 신상품
	@Override
	public void getNewBook(Model model) {
		List<BookDTO> bookEntities = bookmapper.findAll();
		model.addAttribute("newbooks", bookEntities);
	}

	// 신상도서정보
	@Override
	public void getNewIsbn(String isbn, Model model) {
		BookDTO newbook = bookmapper.findIsbn(isbn);
		if (newbook != null) {
			model.addAttribute("book", newbook);
		} else {
			// 도서를 찾지 못했을 경우의 처리
			model.addAttribute("errorMessage", "도서를 찾을 수 없습니다.");
		}
	}

}