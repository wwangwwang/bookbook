document.addEventListener('DOMContentLoaded', function () {
    const questions = [
        {
            question: '가장 선호하는 장르는 무엇인가요?',
            options: ['소설', '비소설', '과학', '판타지'],
            name: 'genre'
        },
        {
            question: '읽고 싶은 책의 분위기는 어떤 건가요?',
            options: ['가벼운', '진지한', '모험적인', '감성적인'],
            name: 'mood'
        },
        {
            question: '책을 읽는 주기는 어떻게 되나요?',
            options: ['자주', '가끔', '드물게', '전혀 안 읽음'],
            name: 'frequency'
        },
        {
            question: '독서할 때 선호하는 매체는 무엇인가요?',
            options: ['종이책', '전자책', '오디오북'],
            name: 'media'
        },
        {
            question: '여행에 대한 흥미는 어떤가요?',
            options: ['매우 흥미로움', '약간 흥미로움', '전혀 흥미로움'],
            name: 'travelInterest'
        },
        {
            question: '어떤 감정을 원하는가요?',
            options: ['행복한', '슬픈', '감동적인', '배우는'],
            name: 'emotion'
        },
        {
            question: '책을 읽는 시간을 주로 언제인가요?',
            options: ['아침', '점심', '저녁', '밤'],
            name: 'readingTime'
        },
        {
            question: '좋아하는 장르는 무엇인가요?',
            options: ['역사', '자기계발', '과학소설', '로맨스'],
            name: 'genre2'
        },
        {
            question: '가장 좋아하는 책의 주제는 무엇인가요?',
            options: ['사랑', '전쟁', '자아 발견', '우정'],
            name: 'theme'
        }
    ];

    let currentQuestionIndex = 0;
    const answers = {};

    function showQuestion(index) {
        const questionContainer = document.getElementById('question-container');
        const question = questions[index];

        questionContainer.innerHTML = `
            <div class="question">
                <p>${question.question}</p>
                ${question.options.map(option => `
                    <label>
                        <input type="radio" name="${question.name}" value="${option}">
                        ${option}
                    </label>
                `).join('')}
            </div>
        `;
    }

    document.getElementById('next-button').addEventListener('click', function () {
        const currentQuestion = questions[currentQuestionIndex];
        const selectedOption = document.querySelector(`input[name="${currentQuestion.name}"]:checked`);

        if (!selectedOption) {
            alert('이 질문에 답해주세요!');
            return;
        }

        answers[currentQuestion.name] = selectedOption.value;
        currentQuestionIndex++;

        if (currentQuestionIndex < questions.length) {
            showQuestion(currentQuestionIndex);
        } else {
            showResult();
        }
    });

    function showResult() {
        let recommendation = '';

        // 선택된 장르
        if (answers.genre) {
            recommendation = `추천 장르: ${answers.genre}`;
        } else {
            recommendation = '추천 장르: 선택하지 않음';
        }

        // 추가적인 장르 추천 로직
        if (answers.genre === '소설') {
            recommendation += ' (추천: 로맨스, 판타지, 스릴러)';
        } else if (answers.genre === '비소설') {
            recommendation += ' (추천: 자서전, 역사, 에세이)';
        } else if (answers.genre === '과학') {
            recommendation += ' (추천: 물리학, 생물학, 천문학)';
        } else if (answers.genre === '판타지') {
            recommendation += ' (추천: 마법, 모험, 신화)';
        } else if (answers.genre === '스릴러') {
            recommendation += ' (추천: 범죄, 심리 스릴러)';
        } else if (answers.genre === '공포') {
            recommendation += ' (추천: 호러, 초자연적)';
        }

        // 결과 표시
        document.getElementById('recommendation').innerText = recommendation;
        document.getElementById('result').style.display = 'block';
        document.getElementById('quiz-form').style.display = 'none'; // 질문 폼 숨김
    }

    // 첫 번째 질문 표시
    showQuestion(currentQuestionIndex);
});
