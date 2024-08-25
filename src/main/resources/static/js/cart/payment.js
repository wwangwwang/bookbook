let merchantUid;
let buyerEmail;
let buyerName;
let amount;
let couponNum;
let couponRate;
let originalAmount;

const amountManager = (function() {
    let currentAmount = 0;

    return {
        updateAmount: function(newAmount) {
            currentAmount = newAmount;
        },
        getCurrentAmount: function() {
            return currentAmount;
        }
    };
})();


//쿠폰 모달창
function openModal() {
    document.getElementById('couponModal').style.display = 'block';
}
function closeModal() {
    document.getElementById('couponModal').style.display = 'none';
}
function selectCoupon() {
    // 선택된 라디오 버튼 가져오기
    const selectedCoupon = document.querySelector('input[name="coupon"]:checked');
    
    if (selectedCoupon) {
        // 선택된 쿠폰의 부모 노드에서 couponRate 값 가져오기
        const couponItem = selectedCoupon.closest('.coupon-item');
        couponRate = couponItem.querySelector('#couponRate').value;
        couponNum = couponItem.querySelector('#couponNum').value;

        //오른쪽 결제 정보에서 쿠폰할인 부분
        document.getElementById('selectedCouponRate').innerText = `- ${couponRate} 원`;
        //쿠폰 적용 알림 부분
        document.getElementById('selectedCoupon').innerText = `${selectedCoupon.value} 쿠폰 적용되었습니다.`;
        //전체 금액도 변경
        originalAmount = document.querySelector(".final-amount").textContent;
        const formatOriginAmount = parseInt(originalAmount.replace(/[^0-9]/g, ''));
        document.querySelector(".final-amount").textContent = 
        	new Intl.NumberFormat('ko-KR').format(formatOriginAmount - couponRate) + " 원";
        	
        const newAmount = formatOriginAmount - couponRate;
	    document.querySelector(".final-amount").textContent =
	        new Intl.NumberFormat('ko-KR').format(newAmount) + " 원";
	
	    amountManager.updateAmount(newAmount);

        closeModal();
        
    } else {
        alert('적용할 쿠폰을 선택해주세요.');
    }
}


//쿠폰 선택 버튼 눌렀을 시
window.onclick = function(event) {
    const modal = document.getElementById('couponModal');
    if (event.target === modal) {
        closeModal();
    }
}


// 최종 결제 금액 계산
document.addEventListener("DOMContentLoaded", function() {
    let totalAmount = 0;
    let cartItems = document.querySelectorAll("tbody tr"); // cartItems에 tbody tr 요소를 모두 가져옴

    // 모든 테이블 행(tbody tr)을 반복
    cartItems.forEach(function(row) {
        // 각 행의 네 번째 열(td:nth-child(3))에서 결제 금액 텍스트를 가져옴
        const itemAmountText = row.querySelector("td:nth-child(3)").textContent;
        
        // 결제 금액에서 숫자만 추출하여 정수로 변환
        const itemAmount = parseInt(itemAmountText.replace(/[^0-9]/g, ''));

        // itemAmount가 NaN이 아닌 경우 totalAmount에 더함
        totalAmount += isNaN(itemAmount) ? 0 : itemAmount;
    });

    //상품 금액을 포맷하여 페이지에 표시
    document.querySelector(".all-price").textContent = 
        new Intl.NumberFormat('ko-KR').format(totalAmount) + " 원";
    
    // 리스트가 비어있지 않은 경우에만 배송비 3000원을 추가
    let finalAmount = totalAmount;
    if (cartItems.length > 0) {
        finalAmount += 3000; // 배송비 추가
    }
    
    document.querySelector(".final-amount").textContent = 
        new Intl.NumberFormat('ko-KR').format(finalAmount) + "원";
    
	merchantUid = document.getElementById('merchantUid').value;
	buyerEmail = document.getElementById("email").value;
	buyerName = document.getElementById("userName").value;
	amount = parseInt(document.querySelector(".final-amount").textContent.replace(/[^0-9]/g, ''));
	buyerTel = document.getElementById("phoneNumber").value;
	
});

//결제 창
IMP.init("imp82310043"); //고객사 식별코드 (userCode)
function requestPay() {
  IMP.request_pay({
    pg: "html5_inicis", //지원 PG사
    pay_method: "card", //결제수단
    merchant_uid: merchantUid, //고객사 고유 주문 번호
    name: "도서", //주문명
    //amount: 100,
    amount: amountManager.getCurrentAmount(), //금액
    buyer_email: buyerEmail,
    popup: true, //결제창 팝업 여부
    buyer_name: buyerName,
    buyer_tel: buyerTel,
    //m_redirect_url: `/payment/completion/${merchantUid}`, //결제완료 후에 이동할 URL
    //notice_url: "", //웹훅 URL
  },
  async (response) => {
	  
      if (!response.success) {
        return alert(`결제에 실패하였습니다. ${response.error_msg}`);
      }
      

       //await fetch(`/payment/completion`, {
       const notified = await fetch('/payment/completion', {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            [header]: token,
          },
          body: JSON.stringify({
			merchantUid : merchantUid,
			amount : amount,
			couponNum : couponNum,
			couponRate : couponRate
            //merchant_uid: response.merchant_uid, // 주문id
            //paid_amount: response.paid_amount, // 결제금액
            //"card_name": response.card_name, // 카드사이름
            //"card_number": response.card_number, // 카드번호
          }),
        })
        if (notified.ok) {
          // POST 요청이 성공하면 리디렉션
          window.location.href = `/payment/completion/${response.merchant_uid}`;
        } else {
          const errorData = await notified.json();
          alert(`결제 검증에 실패하였습니다. 에러 내용: ${errorData.message || "알 수 없는 오류"}`);
        }
    }
  );
}

//////////////////////////////////////
//결제 이후 처리 (아직 사용 X)
async function getAccessToken() {
  const response = await fetch("https://api.iamport.kr/users/getToken", {
    method: "POST",
    headers: { 
		"Content-Type": "application/json",
		[header]: token,
	},
    body: JSON.stringify({
      imp_key: "imp_apikey", // 포트원에서 발급된 API 키
      imp_secret: "ekKoeW8RyKuT0zgaZsUtXXTLQ4AhPFW", // 포트원에서 발급된 API Secret
    }),
  });

  if (!response.ok) {
    throw new Error(`Failed to get access token: ${await response.text()}`);
  }

  const data = await response.json();
  return data.response.access_token;
}

async function getPaymentData(imp_uid, accessToken) {
  const response = await fetch(`https://api.iamport.kr/payments/${imp_uid}`, {
    headers: { Authorization: accessToken },
  });

  if (!response.ok) {
    throw new Error(`Failed to fetch payment data: ${await response.text()}`);
  }

  const paymentData = await response.json();
  return paymentData.response;
}

async function verifyPayment(imp_uid) {
  try {
    // 1. 엑세스 토큰 발급
    const accessToken = await getAccessToken();

    // 2. 결제 정보 조회
    const paymentData = await getPaymentData(imp_uid, accessToken);

    // 3. 주문 데이터와 결제 데이터 비교
    const orderAmount = amount;
    if (orderAmount === paymentData.amount) {
      switch (paymentData.status) {
        case "ready":
          console.log("가상 계좌가 발급된 상태입니다.");
          break;
        case "paid":
          console.log("결제가 완료되었습니다!");
          break;
        default:
          console.log("알 수 없는 결제 상태입니다.");
      }
    } else {
      console.log("결제 금액이 일치하지 않습니다. 위/변조 시도가 의심됩니다.");
    }
  } catch (error) {
    console.error("결제 검증에 실패했습니다. 에러 내용:", error.message || error);
    alert("결제에 실패하였습니다. 에러 내용: " + (error.message || "Unknown error"));
  }
}
