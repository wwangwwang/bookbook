function deleteReview(reviewNum) {
    if (confirm('정말로 이 리뷰를 삭제하시겠습니까?')) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        
        $.ajax({
            url: '/admin/review/' + reviewNum,
            type: 'DELETE',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(result) {
                // 성공적으로 삭제되면 해당 리뷰 행을 DOM에서 제거
                $('#review-' + reviewNum).fadeOut(300, function() {
                    $(this).remove();
                });
                alert('리뷰가 성공적으로 삭제되었습니다.');
                
                // 리뷰 카운트 업데이트 (옵션)
                updateReviewCount();
            },
            error: function(xhr, status, error) {
                alert('리뷰 삭제 중 오류가 발생했습니다: ' + xhr.responseText);
            }
        });
    }
}
