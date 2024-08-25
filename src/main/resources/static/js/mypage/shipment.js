function openAndSubmitForm() {
    // 새 창 열기
    var newWindow = window.open("", "newWindow", "width=600,height=900,left=100,top=100");

    // 새 창에서 DOM 생성
    newWindow.document.write(`
        <html>
        <head>
            <title>자동 제출 폼</title>
        </head>
        <body></body>
        </html>
    `);

    // 폼 요소 생성
    var form = newWindow.document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", "https://info.sweettracker.co.kr/tracking/4");

    // API key 입력 필드
    var apiKeyInput = newWindow.document.createElement("input");
    apiKeyInput.setAttribute("type", "text");
    apiKeyInput.setAttribute("name", "t_key");
    apiKeyInput.setAttribute("value", "mbA3QNjlwjUuAvPuWNUmPw"); // 실제 API key 값으로 변경
    form.appendChild(apiKeyInput);

    // 택배사 코드 입력 필드
    var tCodeInput = newWindow.document.createElement("input");
    tCodeInput.setAttribute("type", "text");
    tCodeInput.setAttribute("name", "t_code");
    tCodeInput.setAttribute("value", "04"); // 실제 택배사 코드 값으로 변경
    form.appendChild(tCodeInput);

    // 운송장 번호 입력 필드
    var tInvoiceInput = newWindow.document.createElement("input");
    tInvoiceInput.setAttribute("type", "text");
    tInvoiceInput.setAttribute("name", "t_invoice");
    tInvoiceInput.setAttribute("value", "686541071202"); // 실제 운송장 번호 값으로 변경
    form.appendChild(tInvoiceInput);

    // 폼을 새 창의 body에 추가
    newWindow.document.body.appendChild(form);

    // 폼 자동 제출
    form.submit();
}