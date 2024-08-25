var client;
let key;
let flag = false; // 챗봇이 열려있는 상태를 추적하는 플래그

// WebSocket 지원 여부를 출력
function isWebSocketSupported() {
    return 'WebSocket' in window;
}

if (isWebSocketSupported()) {
    console.log("이 브라우저는 WebSocket을 지원합니다.");
} else {
    console.log("이 브라우저는 WebSocket을 지원하지 않습니다.");
}

// 시간 및 날짜 포맷 함수
function formatTime(now) {
    var ampm = (now.getHours() > 11) ? "오후" : "오전";
    var hour = now.getHours() % 12;
    if (hour == 0) hour = 12;
    var minute = now.getMinutes();
    var formattedMinute = String(minute).padStart(2, '0');
    return `${ampm} ${hour}:${formattedMinute}`;
}

function formatDate(now) {
    const year = now.getFullYear();
    const month = now.getMonth() + 1; // 월 정보는 0월부터 시작하기 때문에 +1 해줘야 함
    const date = now.getDate();
    const dayOfWeek = now.getDay();
    const days = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    return `${year}년 ${month}월 ${date}일 ${days[dayOfWeek]}`;
}

// 메시지 표시 및 날짜 표시
function showMessage(tag) {
    var chatContent = document.getElementById("chat-content");
    chatContent.innerHTML += tag;
    chatContent.scrollTop = chatContent.scrollHeight;
}

// 날짜가 오늘인 경우, 로컬 스토리지에 저장된 날짜와 비교하여 중복 표시 방지
function showDateIfNew() {
    var now = new Date();
    var today = formatDate(now);
    
    // 로컬 스토리지에서 저장된 마지막 날짜 가져오기
    var savedDate = localStorage.getItem('lastDisplayedDate');
    
    if (savedDate !== today) {
        var dateTag = `<div class="flex center date">${today}</div>`;
        var chatContent = document.getElementById("chat-content");
        chatContent.innerHTML = dateTag + chatContent.innerHTML;
        localStorage.setItem('lastDisplayedDate', today); // 오늘 날짜를 로컬 스토리지에 저장
    }
}

// 환영 메시지 표시 및 저장
function showWelcomeMessage() {
    const now = new Date();
    const today = formatDate(now);

    const welcomeMessage = `<div class="msg bot flex">
                                <div class="icon">
                                    <img src="/img/bot/bot-img.png">
                                </div>
                                <div class="message">
                                <div class="bot-name">북엉이</div>
                                    <div class="part chatbot">
                                        <p>
                                            안녕하세요. <br> 
                                            안내봇 북엉이입니다. 북북. <br>
                                            무엇을 도와드릴까요?
                                        </p>
                                    </div>
                                </div>
                            </div>`;
    
    // 로컬 스토리지에서 환영 메시지를 이미 표시했는지 확인
    var hasShownWelcomeMessage = localStorage.getItem('hasShownWelcomeMessage');
    
    if (!hasShownWelcomeMessage) {
        showMessage(welcomeMessage);
        localStorage.setItem('hasShownWelcomeMessage', 'true');
    }

    localStorage.setItem('lastOpenedDate', today);
    showDateIfNew();
}

// WebSocket 연결 및 처리
function connect() {
    client = Stomp.over(new SockJS('/bookBot'));
    client.connect({}, (frame) => {
        // 연결 성공 시 frame 객체의 정보를 콘솔에 출력
        console.log("Connected to WebSocket server with frame:", frame);
        
        key = new Date().getTime();
        client.subscribe(`/topic/bot/${key}`, (answer) => {
            console.log("응답완료!!!");
            var msgObj = answer.body;
            console.log("Received message from server:", msgObj);
            
            var now = new Date();
            var time = formatTime(now);
			
            var tag = `<div class="msg bot flex">
                        <div class="icon">
                            <img src="/img/bot/bot-img.png">
                        </div>
                        <div class="message">
                        <div class="bot-name">북엉이</div>
                            <div class="part chatbot">
                                <p>${msgObj}</p>
                            </div>
                        </div>
                    </div>`;
            showMessage(tag);
			///////////////////////////////////////////////////////////////////////////추가 html
			if (msgObj.includes("배송조회")) { //includes ""가 포함되어있을경우
                var buttonHTML = `<div class="msg bot flex">
				                        <div class="icon">
				                            <img src="/img/bot/bot-img-none.png">
				                        </div>
				                        <div class="message">
				                            <div class="part chatbot">
				                                <p>아래 버튼을 통해 배송 조회 페이지로 이동해주세요!</p>
												<div class="button-container">
			                                    	<button class="faq-button" onclick="location.href='/mypage/orders';">배송조회 이동</button>
												</div>
				                            </div>
				                            <div class="time">${time}</div>
				                        </div>
				                    </div>`;
                showMessage(buttonHTML);
            }
			if (msgObj.includes("쿠폰조회")) { //includes ""가 포함되어있을경우
                var buttonHTML = `<div class="msg bot flex">
				                        <div class="icon">
				                            <img src="/img/bot/bot-img-none.png">
				                        </div>
				                        <div class="message">
				                            <div class="part chatbot">
				                                <p>아래 버튼을 통해 쿠폰 조회 페이지로 이동해주세요!</p>
												<div class="button-container">
			                                    	<button class="faq-button" onclick="location.href='/mypage/coupons';">쿠폰 조회 이동</button>
												</div>
				                            </div>
				                            <div class="time">${time}</div>
				                        </div>
				                    </div>`;
                showMessage(buttonHTML);
            }
			if (msgObj.includes("베스트셀러")) { //includes ""가 포함되어있을경우
                var buttonHTML = `<div class="msg bot flex">
				                        <div class="icon">
				                            <img src="/img/bot/bot-img-none.png">
				                        </div>
				                        <div class="message">
				                            <div class="part chatbot">
				                                <p>아래 버튼을 통해 베스트셀러 목록으로 이동해주세요!</p>
												<div class="button-container">
			                                    	<button class="faq-button" onclick="location.href='/bookList';">베스트셀러 목록</button>
												</div>
				                            </div>
				                            <div class="time">${time}</div>
				                        </div>
				                    </div>`;
                showMessage(buttonHTML);
            }
			if (msgObj.includes("문의")) { //includes ""가 포함되어있을경우
                var buttonHTML = `<div class="msg bot flex">
				                        <div class="icon">
				                            <img src="/img/bot/bot-img-none.png">
				                        </div>
				                        <div class="message">
				                            <div class="part chatbot">
				                                <p>아래 버튼을 통해 고객문의 접수 도와드릴게요!</p>
												<div class="button-container">
			                                    	<button class="faq-button" onclick="location.href='/mypage/questions';">1:1 문의 접수</button>
												</div>
				                            </div>
				                            <div class="time">${time}</div>
				                        </div>
				                    </div>`;
                showMessage(buttonHTML);
            }
			if (msgObj.includes("룰렛")) { //includes ""가 포함되어있을경우
                var buttonHTML = `<div class="msg bot flex">
				                        <div class="icon">
				                            <img src="/img/bot/bot-img-none.png">
				                        </div>
				                        <div class="message">
				                            <div class="part chatbot">
				                                <p>♚♚룰렛 오브 더 북☆북♚♚가입시$$전원 무료룰렛☜☜100%증정※ ♜북북 오브 북엉이♜펫 무료증정￥ 특정조건 §§북북팀§§★무료룰렛★쿠폰획득기회@@@ 즉시이동</p>
												<div class="button-container">
			                                    	<button class="faq-button" onclick="location.href='/event';"> 이벤트 페이지 이동</button>
												</div>
				                            </div>
				                            <div class="time">${time}</div>
				                        </div>
				                    </div>`;
                showMessage(buttonHTML);
            }
			
			// 이미지 사용시
			if (msgObj.includes("죄송")) { //startsWith ""로 시작할경우
                var imageTag = `<div class="msg bot flex">
				                    <div class="icon">
				                        <img src="/img/bot/bot-img-none.png">
				                    </div>
				                    <div class="message">
										<div class="part chatbot">
											<div class="image-content">
								                <img src="/img/bot/cry-bot-img.png" alt="사과 이미지">
								            </div>
										</div>
				                        <div class="time">${time}</div>
				                    </div>
				                </div>`;
                showMessage(imageTag);
            }
			if (msgObj.includes("안녕")) { //startsWith ""로 시작할경우
                var imageTag = `<div class="msg bot flex">
				                    <div class="icon">
				                        <img src="/img/bot/bot-img-none.png">
				                    </div>
				                    <div class="message">
										<div class="part chatbot">
											<div class="image-content">
								                <img src="/img/bot/happy-bot-img.png" alt="환영 이미지">
								            </div>
										</div>
				                        <div class="time">${time}</div>
				                    </div>
				                </div>`;
                showMessage(imageTag);
            }
            
		   ///////////////////////////////////////////////////////////////////////////////////////
        });
    });
}

// WebSocket 연결 종료
function disconnect() {
    if (client) {
        client.disconnect(() => {
            console.log("Disconnected...");
        });
    }
}

// 상태 저장 및 복원
function saveBotState() {
    var isVisible = document.getElementById("bot-container").classList.contains('open');
    localStorage.setItem('botState', isVisible ? 'open' : 'closed');
}

function loadBotState() {
    // 로컬 스토리지에서 챗봇 상태를 가져옵니다.
    var botState = localStorage.getItem('botState');
    const botContainer = document.getElementById("bot-container");

    if (botState === 'open') {
        botContainer.classList.add('open');
        flag = true;
        connect();
    } else {
        botContainer.classList.remove('open');
        flag = false;
        disconnect();
    }

    // 같은 도메인 내에서 페이지를 이동할 때 환영 메시지를 표시하지 않도록 설정
    var hasShownWelcomeMessage = localStorage.getItem('hasShownWelcomeMessage');
    var wasChatReset = localStorage.getItem('chatReset');
    
    if (!hasShownWelcomeMessage || wasChatReset) {
        if (botState === 'open') {
            showWelcomeMessage();
            localStorage.removeItem('chatReset'); // 채팅 초기화 상태 제거
        }
    }
}

// 페이지를 떠날 때 챗봇 상태를 저장
window.addEventListener('beforeunload', function() {
    saveBotState();
    // 대화 내용 저장을 제거하여 새로고침 시 대화 내용 초기화
    localStorage.removeItem('chatContent');
});

// 버튼 클릭 이벤트 핸들러
function btnCloseClicked() {
    const botContainer = document.getElementById("bot-container");
    botContainer.classList.remove('open'); // Remove the open class to trigger the transition
    saveBotState();
    disconnect();
    flag = false;
    document.getElementById("chat-content").innerHTML = ""; // 채팅 내용 초기화
    localStorage.removeItem('chatContent'); // 로컬 스토리지에서 채팅 내용 제거
    localStorage.setItem('chatReset', 'true'); // 채팅 초기화 상태 저장
    localStorage.removeItem('hasShownWelcomeMessage'); // 환영 메시지 표시 상태 제거
}

function btnBotClicked() {
    if (flag) return;

    const botContainer = document.getElementById("bot-container");
    botContainer.classList.add('open'); // Add the open class to trigger the transition
    connect();
    flag = true;
    
    var hasShownWelcomeMessage = localStorage.getItem('hasShownWelcomeMessage');
    var wasChatReset = localStorage.getItem('chatReset');
    
    if (!hasShownWelcomeMessage || wasChatReset) {
        showWelcomeMessage();
        localStorage.removeItem('chatReset'); // 채팅 초기화 상태 제거
    }
    
    saveBotState();
}

function btnMsgSendClicked() {
    if (!client) {
        console.error("WebSocket client is not initialized.");
        return;
    }

    var question = document.getElementById("question").value.trim();
    if (question.length < 2) {
        alert("질문은 최소 2글자 이상으로 부탁드립니다.");
        return;
    }

    var now = new Date();
    var time = formatTime(now);
    var tag = `<div class="msg user flex">
                <div class="message">
                    <div class="part guest">
                        <p>${question}</p>
                    </div>
                    <div class="time">${time}</div>
                </div>
            </div>`;

    showDateIfNew();
    showMessage(tag);

    var data = {
        key: key,
        content: question,
        userId: 1
    };
    client.send(`/message/bot/question`, {}, JSON.stringify(data));
    clearQuestion();
}

function clearQuestion() {
    document.getElementById("question").value = "";
}

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', (event) => {
	btnCloseClicked();
    loadBotState(); // 챗봇 상태를 로드하고 표시

    document.getElementById("chat-icon").addEventListener('click', btnBotClicked);
    document.getElementById("close-button").addEventListener('click', btnCloseClicked);
    document.getElementById("send-button").addEventListener('click', btnMsgSendClicked);

    document.getElementById("question").addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            btnMsgSendClicked();
        }
    });
});
