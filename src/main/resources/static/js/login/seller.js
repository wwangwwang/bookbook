document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('bookForm');

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        // 폼 데이터 수집
        const formData = new FormData(form);
        const bookData = Object.fromEntries(formData.entries());

        // 여기에서 서버로 데이터를 전송하는 로직을 구현합니다.
        // 예: fetch API를 사용한 POST 요청
        
        console.log('등록된 도서 정보:', bookData);

        // 성공 메시지 표시 (실제 구현에서는 서버 응답에 따라 처리)
        alert('도서가 성공적으로 등록되었습니다!');

        // 폼 초기화
        form.reset();
    });

    // 메뉴 항목 클릭 이벤트 처리
    const menuItems = document.querySelectorAll('.sidebar ul li a');
    menuItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            menuItems.forEach(i => i.classList.remove('active'));
            this.classList.add('active');
            // 여기에 페이지 전환 로직을 추가할 수 있습니다.
            console.log('선택된 메뉴:', this.textContent);
        });
    });
});