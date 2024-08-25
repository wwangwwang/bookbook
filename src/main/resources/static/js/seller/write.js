const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
// 현재 날짜를 형식에 맞춰 placeholder로 설정
function setPlaceholderWithTodayDate() {
    var today = new Date();
    var yyyy = today.getFullYear();
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var dd = String(today.getDate()).padStart(2, '0');

    var formattedDate = yyyy + '-' + mm + '-' + dd;

    document.getElementById('pubdate').placeholder = formattedDate;
}

// 현재 날짜와 시간을 형식에 맞춰 input 값으로 설정
function setPublicationDate() {
    var today = new Date();
    var yyyy = today.getFullYear();
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var dd = String(today.getDate()).padStart(2, '0');
    var hh = String(today.getHours()).padStart(2, '0');
    var min = String(today.getMinutes()).padStart(2, '0');
    var ss = String(today.getSeconds()).padStart(2, '0');

    var formattedDateTime = yyyy + '-' + mm + '-' + dd + ' ' + hh + ':' + min + ':' + ss;

    document.getElementById('pubdate').value = formattedDateTime;
}


function createHiddenInput(name, value) {
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = value;
    return input;
}
// 이미지 파일을 미리보기로 설정
function fileUpload(input){
	const files=input.files;
	if(files.length<1){
		console.log("파일이 선택되지 않았어요")
		return;
	}
	//console.log(files[0]);
	//const fileName = files[0].name;
	const fileType = files[0].type;
	if(!fileType.startsWith('image/')){
		alert("이미지 파일 이 아닙니다.");
		input.value='';
		return;
	}
	const fileSize = files[0].size;
	if(fileSize> 2*1024*1024){
		alert("파일용량제한: 2MB이내의 파일을 사용하세요");
		input.value='';
		return;
	}
	var formData=new FormData();
	formData.append("bookImgInput",input.files[0])
	
	//미리 temp 업로드 
	uploadImage("/seller/inventory/fileupload",  formData)
		.then(result=> {
			const url = result.url;
	        const tempKey = result.tempKey;
	        const orgName = result.orgName;
	
	        // Create and append hidden inputs dynamically
	        const bucketKeyInput = createHiddenInput('tempKey', tempKey);
	        const orgNameInput = createHiddenInput('orgName', orgName);
	
	        // Append hidden inputs to the form (assumed to be the parent form of the file input)
	        const label = input.parentElement;
            label.appendChild(bucketKeyInput);
            label.appendChild(orgNameInput);
	        // Update the display of the image and other fields
	        label.style.backgroundImage = `url(${url})`;
	        label.style.backgroundColor = "transparent";
		})
		.catch(error => {
			alert("파일업로드 실패! : "+error.response.status);
		});
}

// 폼 제출 시 S3에 이미지 업로드
function uploadImage(url, formData){
	return fetch(url , {
	        method: "POST",
	        headers: {
	            [header]: token //이 부분만 추가하면 됨 // CSRF 토큰을 헤더에 추가
	        },
	        body: formData
	    })
	//.then(response => response.text()) 
	.then(response => response.json()) 
	.catch(error => {
		console.log('Error: ',error);
		throw error; //에러를 호출할 곳으로 전달
	}) 
	//.finally() 
	;
}

/*
// 페이지가 로드된 후, placeholder와 날짜 입력 값을 설정하는 함수 호출
window.onload = function() {
    setPlaceholderWithTodayDate();
    setPublicationDate();

    // 폼 제출 이벤트에 대해 submitForm 함수를 호출하도록 설정
    document.querySelector('form').addEventListener('submit', submitForm);
}
*/
