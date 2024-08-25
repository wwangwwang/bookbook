function addToWishlist(isbn, button) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    fetch('/api/books/wish', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            [csrfHeader]: csrfToken
        },
        body: new URLSearchParams({isbn: isbn})
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text) });
        }
        return response.text();
    })
    .then(data => {
        console.log('클라이언트: 성공:', data);
        alert("즐겨찾기 추가되었습니다.");
        // UI 업데이트
        const icon = button.querySelector('i');
        icon.classList.remove('far');
        icon.classList.add('fas');
        button.disabled = true;
    })
    .catch((error) => {
        console.error('클라이언트: 오류:', error);
        alert('위시리스트 추가 중 오류가 발생했습니다: ' + error.message);
    });
}

document.addEventListener('DOMContentLoaded', function() {
    const wishButtons = document.querySelectorAll('.heart-button');
    wishButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            const isbn = this.getAttribute('data-isbn');
            addToWishlist(isbn, this);
        });
    });
});