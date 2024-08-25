document.addEventListener('DOMContentLoaded', function() {
    const quantityInput = document.querySelector('.quantity-selector input');
    const decreaseBtn = document.querySelector('.decrease');
    const increaseBtn = document.querySelector('.increase');
    const totalPriceSpan = document.querySelector('.total-price');
    const unitPrice = document.querySelector('.price').getAttribute("data-price"); // 단위 가격
    const hiddenQuantityInput = document.querySelector('input[name="quantity"][type="hidden"]');

    function updateTotalPrice() {
        const quantity = parseInt(quantityInput.value);
        const totalPrice = quantity * parseInt(unitPrice);
        totalPriceSpan.textContent = totalPrice.toLocaleString() + '원';
        hiddenQuantityInput.value = quantity; // hidden input 업데이트
    }

    decreaseBtn.addEventListener('click', function() {
        let value = parseInt(quantityInput.value);
        if (value > 1) {
            quantityInput.value = value - 1;
            updateTotalPrice();
        }
    });

    increaseBtn.addEventListener('click', function() {
        let value = parseInt(quantityInput.value);
        quantityInput.value = value + 1;
        updateTotalPrice();
    });

    // 초기 총 가격 설정
    updateTotalPrice();
});