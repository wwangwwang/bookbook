function validateForm() {
	

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
