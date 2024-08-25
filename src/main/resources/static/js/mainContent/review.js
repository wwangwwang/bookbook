$(document).ready(function() {
    console.log("리뷰 스크립트 로드됨");

    // CSRF 토큰 가져오기
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    // 리뷰 작성 버튼 클릭 이벤트
    $('#writeReviewBtn').on('click', function() {
        console.log("리뷰 작성 버튼 클릭됨");
        $('#reviewModal').css('display', 'block');
    });

    // 모달 닫기 버튼 클릭 이벤트
    $('.close').on('click', function() {
        console.log("닫기 버튼 클릭됨");
        $('#reviewModal').css('display', 'none');
    });

    // 모달 외부 클릭 시 닫기
    $(window).on('click', function(event) {
        if (event.target == $('#reviewModal')[0]) {
            $('#reviewModal').css('display', 'none');
        }
    });

    // 리뷰 폼 제출 이벤트
    $('#reviewForm').off('submit').on('submit', function(e) {
        e.preventDefault();
        console.log("리뷰 폼 제출됨");

        const content = $('#reviewContent').val();
        const rate = $('input[name="rate"]:checked').val();
        const isbn = $('#bookIsbn').val();

        console.log("폼에서 가져온 데이터:", { content, rate, isbn });

        if (!content || !rate) {
            console.error("리뷰 내용 또는 평점이 비어있습니다.");
            alert("리뷰 내용과 평점을 모두 입력해주세요.");
            return;
        }

        const reviewData = {
            reviewContent: content,
            rate: parseInt(rate)
        };

        console.log("서버로 전송할 데이터:", JSON.stringify(reviewData));

        $.ajax({
            url: `/detail/${isbn}/review`,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(reviewData),
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
                console.log("CSRF 토큰 헤더 추가됨:", header, token);
            },
            success: function(response) {
                console.log('리뷰 제출 성공. 응답:', response);
                $('#reviewModal').css('display', 'none');
                addReviewToList(response);
                // 폼 리셋
                $('#reviewContent').val('');
                $('input[name="rate"]').prop('checked', false);
            },
            error: function(xhr, status, error) {
                console.error("리뷰 제출 실패. 상태:", status, "오류:", error);
                console.error("응답 텍스트:", xhr.responseText);
                alert("리뷰 작성에 실패했습니다. 다시 시도해 주세요.");
            }
        });
    });

    // 평점 선택 시 시각적 피드백
    $('input[name="rate"]').on('change', function() {
        const rate = $(this).val();
        $('.rating label').removeClass('selected');
        $(this).siblings('label').addClass('selected');
        $(this).siblings('label').prevAll('label').addClass('selected');
    });

    // 폼 입력 값 변경 시 실시간 유효성 검사
    $('#reviewContent, input[name="rate"]').on('change', function() {
        const content = $('#reviewContent').val();
        const rate = $('input[name="rate"]:checked').val();

        if (content && rate) {
            $('#reviewSubmitBtn').prop('disabled', false);
        } else {
            $('#reviewSubmitBtn').prop('disabled', true);
        }
    });

    // 리뷰를 목록에 추가하는 함수
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
        displayStars(); // 별점 표시 함수 호출
    }

    // 별점 표시 함수
    function displayStars() {
        $('.star-rating').each(function() {
            const rating = $(this).data('rating');
            const stars = '★'.repeat(rating) + '☆'.repeat(5 - rating);
            $(this).text(stars);
        });
    }

    // 페이지 로드 시 초기 별점 표시
    displayStars();

    // 전체 리뷰 / 구매자 리뷰 탭 전환
    $('#allReviewsBtn').on('click', function() {
        $(this).addClass('active');
        $('#buyerReviewsBtn').removeClass('active');
        // 여기에 전체 리뷰를 보여주는 로직 추가
    });

    $('#buyerReviewsBtn').on('click', function() {
        $(this).addClass('active');
        $('#allReviewsBtn').removeClass('active');
        // 여기에 구매자 리뷰만 보여주는 로직 추가
    });
});