//csrf 토큰
const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

//탈퇴 버튼 눌렀을 시
async function accountDelete(){
	if(confirm("정말 탈퇴하시겠습니까?\n탈퇴 후 동일 아이디로 재가입이 불가합니다.")){
		const password = document.getElementById('password').value;
		
		try{
			const response = await fetch('/mypage/account/delete',{
				method : 'PUT',
				headers : {
					'Content-Type': 'application/x-www-form-urlencoded',
					[header]: token
				},
				body : new URLSearchParams({ password })
			})
			
			const result = await response.text();
			
			if(result=="성공적으로 탈퇴 처리 되었습니다."){
	       		alert(result);
	        	window.location.href = '/logout';
				
			} else{
				alert(result);
				window.location.href = '/mypage/account/delete';
			}

			
		} catch{
			console.log('An error occurred: ' + error.message);
		}
	}
}