const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

function editInfo() {
	// 모든 p 태그를 숨기고, 해당 input 태그를 표시
	const fields = ['telNum', 'businessNum', 'bank', 'account', 'accountHolder', 'userName', 'userRRN', 'email', 'phoneNumber', 'birthDate', 'address', 'detailAddress', 'extraAddress'];
	fields.forEach(field => {
		document.getElementById(field + 'Text').style.display = 'none';
		document.getElementById(field + 'Input').style.display = 'block';
	});

	// '정보 수정' 버튼 숨기기, '저장' 버튼 보이기
	document.getElementById('editButton').style.display = 'none';
	document.getElementById('saveButton').style.display = 'block';
}

/*
// CSRF 토큰을 쿠키에서 읽어오는 함수
function getCsrfTokenFromCookie() {
	const csrfTokenName = '_csrf'; // CSRF 토큰의 이름 (Spring Security의 기본값)
	const cookies = document.cookie.split(';');

	for (let i = 0; i < cookies.length; i++) {
		const cookie = cookies[i].trim();
		if (cookie.startsWith(csrfTokenName + '=')) {
			return cookie.substring(csrfTokenName.length + 1);
		}
	}
	return null; // CSRF 토큰이 쿠키에 없을 경우
}
*/

/*function submitForm(event) {
	event.preventDefault();

	const form = document.getElementById('infoForm');
	const formData = new FormData(form);

	fetch('/seller/mypage', {
		method: 'PUT',
		headers: {
			[header]: token, //이 부분만 추가하면 됨
			'X-Requested-With': 'XMLHttpRequest'
		},
		body: formData
	})
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('403 Forbidden');
			}
		})
		.then(data => {
			if (data.success) {
				alert('정보가 성공적으로 저장되었습니다.');

				// 입력된 값으로 p 태그 갱신
				const fields = ['businessNum', 'bank', 'account', 'accountHolder', 'userName', 'userRRN', 'email', 'phoneNumber', 'birthDate', 'address', 'detailAddress', 'extraAddress'];
				fields.forEach(field => {
					document.getElementById(field + 'Text').innerText = document.getElementById(field + 'Input').value;
				});

				// 필드 비활성화: p 태그를 보이게 하고 input 태그를 숨김
				fields.forEach(field => {
					document.getElementById(field + 'Text').style.display = 'block';
					document.getElementById(field + 'Input').style.display = 'none';
				});

				// 버튼 상태 변경
				document.getElementById('editButton').style.display = 'block';
				document.getElementById('saveButton').style.display = 'none';
			} else {
				alert('정보 저장에 실패했습니다. 다시 시도해주세요.');
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('정보 저장에 실패했습니다. 다시 시도해주세요.');
		});
}*/