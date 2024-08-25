package com.project.bookbook.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bookbook.domain.dto.ReviewDTO;
import com.project.bookbook.domain.entity.BookEntity;
import com.project.bookbook.domain.entity.ReviewEntity;
import com.project.bookbook.domain.entity.UserEntity;
import com.project.bookbook.domain.repository.BookRepository;
import com.project.bookbook.domain.repository.ReviewRepository;
import com.project.bookbook.domain.repository.UserRepository;
import com.project.bookbook.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceProcess implements ReviewService {

	private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private ReviewDTO convertToDTO(ReviewEntity review) {
        return ReviewDTO.builder()
                .reviewNum(review.getReviewNum())
                .username(review.getUser().getUserName())
                .reviewContent(review.getReviewContent())
                .rate(review.getRate())
                .reviewDate(review.getReviewDate())
                .recommend(review.getRecommend())
                .complaint(review.getComplaint())
                .actualOrder(review.getActualOrder() == 1)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByIsbn(String isbn) {
        List<ReviewEntity> reviews = reviewRepository.findByBookIsbnOrderByReviewDateDesc(isbn);
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewDTO createReview(long userId, String isbn, String reviewContent, int rate) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BookEntity book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        ReviewEntity review = ReviewEntity.builder()
                .user(user)
                .book(book)
                .reviewContent(reviewContent)
                .rate(rate)
                .build();

        ReviewEntity savedReview = reviewRepository.save(review);
        return convertToDTO(savedReview);
    }

    @Override
    @Transactional(readOnly = true)
    public double calculateAverageRating(String isbn) {
        List<ReviewEntity> reviews = reviewRepository.findByBookIsbn(isbn);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double sum = reviews.stream().mapToInt(ReviewEntity::getRate).sum();
        return sum / reviews.size();
    }

    @Override
    @Transactional(readOnly = true)
    public int getReviewCount(String isbn) {
        return reviewRepository.countByBookIsbn(isbn);
    }
}
