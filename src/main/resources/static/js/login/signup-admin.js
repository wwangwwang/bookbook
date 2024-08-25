// CSRF 토큰 처리
var csrfToken = $("meta[name='_csrf']").attr("content");
var csrfHeader = $("meta[name='_csrf_header']").attr("content");

function uploadBusinessReg(input) {
    if (input.files && input.files[0]) {
        var file = input.files[0];

        if (file.type !== "image/png") {
            alert("PNG 파일만 업로드 가능합니다.");
            input.value = "";
            return;
        }

        var formData = new FormData();
        formData.append("file", file);

        $.ajax({
            url: '/api/upload',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(response) {
                $('#businessRegImageId').val(response.id);

            },
            error: function(xhr, status, error) {
                console.error("Upload failed:", status, error);
                $('#upload-status').text('업로드 실패: ' + (xhr.responseJSON ? xhr.responseJSON.error : error));
            }
        });
    }
}

function validatePublisherForm() {
    // 출판사 이름 유효성 검사
    var publisherNameInput = document.getElementById("publisher-name");
    var publisherNamePattern = /^[가-힣a-zA-Z0-9\s]{2,50}$/;
    if (!publisherNamePattern.test(publisherNameInput.value)) {
        alert("출판사 이름은 2~50자의 한글, 영문, 숫자만 사용할 수 있습니다.");
        publisherNameInput.focus();
        return false;
    }

    // 출판사 번호 유효성 검사
    var publisherNumberInput = document.getElementById("publisher-number");
    var publisherNumberPattern = /^[0-9]{2,20}$/;
    if (!publisherNumberPattern.test(publisherNumberInput.value)) {
        alert("출판사 번호는 숫자만 2~20자 이내로 입력할 수 있습니다.");
        publisherNumberInput.focus();
        return false;
    }

    // 사업자 번호 유효성 검사
    var businessNumberInput = document.getElementById("business-number");
    var businessNumberPattern = /^\d{3}-\d{2}-\d{5}$/;
    if (!businessNumberPattern.test(businessNumberInput.value)) {
        alert("사업자 번호 형식은 123-45-67890 입니다.");
        businessNumberInput.focus();
        return false;
    }

    // 사업자 등록증 유효성 검사
    var businessRegistrationInput = document.getElementById("business-registration");
    if (businessRegistrationInput.files.length === 0) {
        alert("사업자 등록증 파일을 업로드해야 합니다.");
        businessRegistrationInput.focus();
        return false;
    }

    // 계좌 번호 유효성 검사
    var accountNumberInput = document.getElementById("account-number");
    var accountNumberPattern = /^\d{2,3}-\d{3,6}-\d{1,7}$/;
    if (!accountNumberPattern.test(accountNumberInput.value)) {
        alert("계좌 번호는 '은행 코드(2~3자리) - 계좌번호(3~6자리) - 숫자(1~7자리)' 형식으로 입력해야 합니다.");
        accountNumberInput.focus();
        return false;
    }

    // 모든 유효성 검사를 통과하면 폼 제출
    document.querySelector('form').submit();
    return true;
}
