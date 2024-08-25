//장바구니에 담은 상품 삭제
function deleteCartItem(cartDetailNum) {
	
    if (confirm("정말로 삭제하시겠습니까?")) { // 사용자에게 확인 요청
        const deleteUrl = `/cart/${cartDetailNum}`;

        fetch(deleteUrl, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            }
        })
        .then(response => {
		    if (response.ok) {
		        alert("삭제되었습니다.");
		    	window.location.reload(); // 페이지를 새로고침하여 변경 사항 반영
		    } else {
		        throw new Error('삭제 과정에서 오류가 발생했습니다.. '); //http 오류
		    }
		})
		.catch(error => {
		    console.error('Error:', error);
		    alert("삭제 과정에서 오류가 발생했습니다. ");
		});
    }
}


//전체 상품 구매 (장바구니에 담은 상품 전체 결제로 보내기)
function paymentAll() {
    // cartList 내의 모든 tr 요소에서 cartDetailNum 추출
    const cartItems = document.querySelectorAll('#cartList tr');
    const cartDetailNums = Array.from(cartItems).map(item => item.getAttribute('data-cart-detail-num'));

    fetch('/cart/all', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify({ cartDetailNums })  // cartDetailNums 배열을 JSON으로 변환
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('장바구니에 상품이 없습니다.');
        }
        return response.text(); // 응답을 텍스트로 변환
    })
    .then(merchantUid => {
        // 응답에서 orderNum 추출 후 결제 페이지로 리디렉션
        window.location.href = `/payment/${merchantUid}`;
    })
    .catch(error => {
		console.log(error);
        alert('장바구니에 상품이 없습니다.');
    });
}


// 최종 결제 금액 계산
document.addEventListener("DOMContentLoaded", function() {
    let totalAmount = 0;
    let cartItems = document.querySelectorAll("tbody tr"); // cartItems에 tbody tr 요소를 모두 가져옴

    // 모든 테이블 행(tbody tr)을 반복
    cartItems.forEach(function(row) {
        // 각 행의 세 번째 열(td:nth-child(3))에서 결제 금액 텍스트를 가져옴
        const itemAmountText = row.querySelector("td:nth-child(3)").textContent;
        
        // 결제 금액에서 숫자만 추출하여 정수로 변환
        const itemAmount = parseInt(itemAmountText.replace(/[^0-9]/g, ''));

        // itemAmount가 NaN이 아닌 경우 totalAmount에 더함
        totalAmount += isNaN(itemAmount) ? 0 : itemAmount;
    });

    // 최종 결제 금액을 포맷하여 페이지에 표시
    document.querySelector(".all-price").textContent = 
        new Intl.NumberFormat('ko-KR').format(totalAmount) + " 원";
    
    // 리스트가 비어있지 않은 경우에만 배송비 3000원을 추가
    let finalAmount = totalAmount;
    if (cartItems.length > 0) {
        finalAmount += 3000; // 배송비 추가
    }
    
    document.querySelector(".final-amount").textContent = 
        new Intl.NumberFormat('ko-KR').format(finalAmount) + "원";
        
    
});


//수량 변경 후 db에 저장
document.addEventListener('DOMContentLoaded', function() {
    const cartList = document.getElementById('cartList');

    cartList.addEventListener('click', function(event) {
        if (event.target.classList.contains('change-quantity-btn')) {
            const row = event.target.closest('tr');
            const quantityControl = row.querySelector('.quantity-control');
            const quantitySpan = row.querySelector('td:nth-child(2) > span');
            const changeButton = row.querySelector('.change-quantity-btn');
            const cancelConfirmButtons = row.querySelector('.cancel-confirm-buttons');
            const deleteButton = row.querySelector('.delete');

            quantitySpan.style.display = 'none';
            quantityControl.style.display = 'flex';
            changeButton.style.display = 'none';
            cancelConfirmButtons.style.display = 'block';
            deleteButton.style.display = 'none';
        } else if (event.target.classList.contains('cancel-btn')) {
            resetQuantityControl(event.target.closest('tr'));
        } else if (event.target.classList.contains('confirm-btn')) {
            updateQuantity(event.target.closest('tr'));
        }
    });

    function resetQuantityControl(row) {
        const quantityControl = row.querySelector('.quantity-control');
        const quantitySpan = row.querySelector('td:nth-child(2) > span');
        const changeButton = row.querySelector('.change-quantity-btn');
        const cancelConfirmButtons = row.querySelector('.cancel-confirm-buttons');
        const deleteButton = row.querySelector('.delete');
        const quantityInput = row.querySelector('.quantity-input');

        quantitySpan.style.display = 'inline';
        quantityControl.style.display = 'none';
        changeButton.style.display = 'inline-block';
        cancelConfirmButtons.style.display = 'none';
        deleteButton.style.display = 'inline-block';
        quantityInput.value = quantitySpan.textContent.replace('개', '');

        // Reset the price to its original value
        updatePrice(row, parseInt(quantitySpan.textContent));
    }

    function updateQuantity(row) {
        const quantityInput = row.querySelector('.quantity-input');
        const cartDetailNum = row.dataset.cartDetailNum;
        const quantity = parseInt(quantityInput.value);

        fetch('/cart/update', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                [header] : token
            },
            body: JSON.stringify({
                cartDetailNum: cartDetailNum,
                quantity: quantity
            })
        })
        .then(response => response.text())
        .then(data => {
            if (data.success) {
                // 페이지 새로고침
                window.location.reload();
            } else {
                console.error('Failed to update quantity');
                resetQuantityControl(row);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            resetQuantityControl(row);
        });
    }

    function updatePrice(row, quantity) {
	    const priceElement = row.querySelector('.price');
	    const unitPriceStr = priceElement.getAttribute('data-unit-price');
	    console.log('Raw Unit Price:', unitPriceStr);
	    
	    const unitPrice = parseFloat(unitPriceStr);
	    
	    console.log('Parsed Unit Price:', unitPrice);
	    console.log('Quantity:', quantity);
	
	    if (isNaN(unitPrice)) {
	        console.error('Unit price is not a number. Check data-unit-price attribute.');
	        return;
	    }
	
	    const totalPrice = unitPrice * quantity;
	    console.log('Total Price:', totalPrice);
	
	    priceElement.textContent = `${totalPrice.toLocaleString()}원`;
	}

    document.querySelectorAll('.quantity-control').forEach(function(control) {
	    const decrementButton = control.querySelector('.decrement');
	    const incrementButton = control.querySelector('.increment');
	    const quantityInput = control.querySelector('.quantity-input');
	    const row = control.closest('tr');
	
	    decrementButton.addEventListener('click', function() {
	        let currentValue = parseInt(quantityInput.value);
	        if (currentValue > 1) {
	            currentValue -= 1;
	            quantityInput.value = currentValue;
	            updatePrice(row, currentValue);
	        }
	    });
	
	    incrementButton.addEventListener('click', function() {
	        let currentValue = parseInt(quantityInput.value);
	        currentValue += 1;
	        quantityInput.value = currentValue;
	        updatePrice(row, currentValue);
	    });
	
	    // Initialize price on load
	    const initialQuantity = parseInt(quantityInput.value);
	    updatePrice(row, initialQuantity);
	});
});