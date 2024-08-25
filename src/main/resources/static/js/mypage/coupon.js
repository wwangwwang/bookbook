//csrf 토큰
//const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
//const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

//사용가능한 쿠폰, 만료된 쿠폰 페이지 이동
function openTab(evt, tabName) {
	var i, tabcontent, tablinks;
	tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tab");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}
	document.getElementById(tabName).style.display = "block";
	evt.currentTarget.className += " active";
}

//처음 들어오면 default로 "쿠폰"페이지
document.addEventListener("DOMContentLoaded", function() {
	document.querySelector(".tab").click();
});

//체크 표시 누르면 색깔 바뀌고, 전체 부분 누르면 모든 체크표시 선택
document.addEventListener('DOMContentLoaded', () => {
    const allBtn = document.getElementById('all-btn');
    const allText = document.getElementById('all-text');
    const couponIcons = document.querySelectorAll('.coupon .discount i');
	
	// 버튼과 '전체' 텍스트에 동일한 함수 할당
    const toggleSelection = () => {
        allBtn.classList.toggle('selected');
        couponIcons.forEach(icon => {
            icon.classList.toggle('selected');
        });
    };

    allBtn.addEventListener('click', toggleSelection);
    allText.addEventListener('click', toggleSelection);
    
    couponIcons.forEach(icon => {
        icon.addEventListener('click', () => {
            icon.classList.toggle('selected');
        });
    });
});

//모달 창 : 쿠폰 등록
var modalReg = document.getElementById("modal-register");
var btn = document.getElementById("RegisterBtn");
var close = document.getElementById("coupon-register-close");

console.log(modalReg);
console.log(btn);

btn.onclick = function() {
	modalReg.style.display = "block";
}

close.onclick = function() {
    modalReg.style.display = "none";
}

//모달 창 : 쿠폰 상세 보기
var modal = document.getElementById("myModal");
var btns = document.getElementsByClassName("details");
var span = document.getElementsByClassName("close")[2];

for (var i = 0; i < btns.length; i++) {
    btns[i].onclick = function() {
        modal.style.display = "block";
    }
}

span.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

//쿠폰 추가할때 체크
async function registerCoupon() {
	
    const couponNum = document.getElementById('couponNum').value;

    try {
        const response = await fetch('/mypage/coupons', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                [header]: token
            },
            body: new URLSearchParams({ couponNum })
        });

        const result = await response.text();

        alert(result); //서버에서 가져온 message 출력
        window.location.href = '/mypage/coupons';
    } catch (error) {
        console.log('An error occurred: ' + error.message);
    }
}