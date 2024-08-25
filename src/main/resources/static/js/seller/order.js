const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
/*
document.addEventListener('DOMContentLoaded', function() {
    const statusChangeButtons = document.querySelectorAll('.status-change-btn');

    statusChangeButtons.forEach(button => {
        button.addEventListener('click', function() {
            const merchantUid = button.getAttribute('data-merchant-uid');
            const currentStatus = button.getAttribute('data-current-status');

            if (currentStatus == 1) {
                fetch(`/seller/order`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        
                    },
                    body: JSON.stringify({
                        merchantUid: merchantUid,
                        orderStatus: 2
                    })
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        button.parentElement.previousElementSibling.textContent = 2;
                    } else {
                        alert('상태 변경에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
            } else {
                alert('현재 상태가 1이 아니어서 변경할 수 없습니다.');
            }
        });
    });
});
*/