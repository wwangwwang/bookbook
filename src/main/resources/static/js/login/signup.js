// CSRF 토큰 처리
function submitAndRedirect() {
    var form = $('#signupForm');
    var csrfToken = $("input[name='_csrf']").val();
    var csrfHeader = $("input[name='_csrf']").attr('name');
    
    console.log("CSRF Token:", csrfToken);
    console.log("CSRF Header:", csrfHeader);
    
    $.ajax({
        url: '/signup/save-and-redirect',
        type: 'POST',
        data: form.serialize(),
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(response) {
            console.log("Response:", response);
            if (response === "success") {
                window.location.href = '/signup/admin';
            } else {
                alert("오류가 발생했습니다.");
            }
        },
        error: function(xhr, status, error) {
            console.error("Error occurred:", error);
            console.error("Status:", status);
            console.error("Response:", xhr.responseText);
            alert("오류가 발생했습니다.");
        }
    });
}


// Form validation
function validateForm() {
    // 이메일 유효성 검사
    var emailInput = document.getElementById("signup-email");
    var emailPattern = /^.+@.+\..+$/;
    if (emailInput.value !== "" && !emailPattern.test(emailInput.value)) {
        alert("이메일 형식이 올바르지 않습니다.");
        emailInput.focus();
        return false;
    }

    // 비밀번호 유효성 검사
    var pwInput = document.getElementById("signup-pw");
    var pwPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*(),.?:{}|<>]).{8,16}$/;
    if (!pwPattern.test(pwInput.value)) {
        alert("비밀번호는 8~16자리 영문, 숫자, 특수문자를 포함해야 합니다.");
        pwInput.focus();
        return false;
    }

    // 이름 유효성 검사
    var nameInput = document.getElementById("signup-name");
    var namePattern = /^[가-힣]{2,5}$/;
    if (!namePattern.test(nameInput.value)) {
        alert("이름은 2~5자리 한글만 사용할 수 있습니다.");
        nameInput.focus();
        return false;
    }

    // 생년월일 유효성 검사
    var birthInput = document.getElementById("signup-birth");
    var birthPattern = /^(19|20)\d\d-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$/;
    if (!birthPattern.test(birthInput.value)) {
        alert("생년월일 형식이 올바르지 않습니다.");
        birthInput.focus();
        return false;
    }

    // 주민등록번호 유효성 검사
    var ssnInput = document.getElementById("signup-ssn");
    var ssnPattern = /^\d{6}-[1-4]\d{6}$/;
    if (!ssnPattern.test(ssnInput.value)) {
        alert("주민등록번호 형식이 올바르지 않습니다.");
        ssnInput.focus();
        return false;
    }

    // 휴대전화 유효성 검사
    var phoneInput = document.getElementById("signup-phone");
    var phonePattern = /^01[0-9]-[0-9]{4}-[0-9]{4}$/;
    if (!phonePattern.test(phoneInput.value)) {
        alert("휴대전화 번호 형식이 올바르지 않습니다.");
        phoneInput.focus();
        return false;
    }

    // 모든 유효성 검사를 통과하면 폼 제출
    document.querySelector('form').submit();
    return true;
}
