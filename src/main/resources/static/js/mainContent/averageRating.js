function displayStars() {
    $('.stars').each(function() {
        const rating = parseFloat($(this).data('rating'));
        updateStarDisplay($(this), rating);
    });
}

function updateStarDisplay($element, rating) {
    const starPercentage = (rating / 5) * 100;
    const starPercentageRounded = `${Math.round(starPercentage / 10) * 10}%`;
    $element.html(`<div class="stars-inner" style="width: ${starPercentageRounded}"></div>`);
    $element.siblings('.rating-score').text(rating.toFixed(1));
}

function updateRatingDisplay(newRating, newReviewCount) {
    const $bookRating = $('.book-rating');
    const $stars = $bookRating.find('.stars');
    const $reviewCount = $bookRating.find('.review-count');

    updateStarDisplay($stars, newRating);
    $reviewCount.text(`리뷰 ${newReviewCount}개`);
    $stars.data('rating', newRating);
}

// 페이지 로드 시 별점 표시
$(document).ready(function() {
    displayStars();
});

// 리뷰 제출 후 별점 업데이트
$('#reviewForm').on('submit', function(e) {
    e.preventDefault();
    
    // 기존의 리뷰 제출 AJAX 코드
    $.ajax({
        url: $(this).attr('action'),
        method: 'POST',
        data: $(this).serialize(),
        success: function(response) {
            // 리뷰 제출 성공 처리
            $('#reviewModal').css('display', 'none');
            $('#reviewContent').val('');
            $('input[name="rate"]').prop('checked', false);

            // 새로운 리뷰를 목록에 추가
            addReviewToList(response);

            // 평균 평점과 리뷰 수 업데이트
            const currentRating = parseFloat($('.stars').data('rating'));
            const currentReviewCount = parseInt($('.review-count').text().match(/\d+/)[0]);
            
            const newReviewCount = currentReviewCount + 1;
            const newRating = ((currentRating * currentReviewCount) + response.rate) / newReviewCount;
            
            updateRatingDisplay(newRating, newReviewCount);
        },
        error: function(xhr, status, error) {
            console.error("리뷰 제출 실패:", error);
            alert("리뷰 작성에 실패했습니다. 다시 시도해 주세요.");
        }
    });
});

function addReviewToList(review) {
    const reviewHtml = `
        <div class="review-item">
            <div class="review-header">
                <h3>${review.username}</h3>
                <div class="stars-display">
                    <span class="star-rating" data-rating="${review.rate}"></span>
                </div>
            </div>
            <p>${review.reviewContent}</p>
        </div>
    `;
    $('#reviewList').prepend(reviewHtml);
    displayStars(); // 새로 추가된 리뷰의 별점 표시
}