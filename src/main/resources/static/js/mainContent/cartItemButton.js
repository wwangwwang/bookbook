function addToCartItems(form, event) {
    event.preventDefault();
    const isbn = form.querySelector('input[name="isbn"]').value;
    const quantityInput = form.querySelector('input[name="quantity"]');
    const quantity = quantityInput ? quantityInput.value : '1';  // 수량 입력 필드가 없으면 '1' 사용
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    console.log("Client: Sending request to add book to CartItems: " + isbn + ", Quantity: " + quantity);

    fetch(form.action, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            [csrfHeader]: csrfToken
        },
        body: `isbn=${encodeURIComponent(isbn)}&quantity=${encodeURIComponent(quantity)}`
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Server responded with status: ' + response.status);
        }
        return response.text();
    })
    .then(data => {
        console.log('Client: Success: Book added to cart', data);
        // 성공 시 UI 업데이트
        const cartButton = form.querySelector('.cart-button');
        cartButton.textContent = '장바구니에 추가됨';
        cartButton.disabled = true;
        cartButton.classList.add('added-to-cart');
    })
    .catch((error) => {
        console.error('Client: Error:', error);
        alert('장바구니에 추가하는 중 오류가 발생했습니다: ' + error.message);
    });

    return false;
}