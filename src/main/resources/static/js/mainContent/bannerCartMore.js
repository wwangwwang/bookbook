document.addEventListener('DOMContentLoaded', () => {
	const slider = document.querySelector('.slider');
	const slides = document.querySelectorAll('.slide');
	const prevBtn = document.querySelector('.prev');
	const nextBtn = document.querySelector('.next');
	const indicators = document.querySelector('.indicators');

	let currentIndex = 0;
	let isTransitioning = false;
	let autoSlideInterval;

	function createIndicators() {
		slides.forEach((_, index) => {
			const indicator = document.createElement('div');
			indicator.classList.add('indicator');
			indicator.addEventListener('click', () => goToSlide(index));
			indicators.appendChild(indicator);
		});
		updateIndicators();
	}

	function updateIndicators() {
		document.querySelectorAll('.indicator').forEach((indicator, index) => {
			indicator.classList.toggle('active', index === currentIndex);
		});
	}

	function loadImage(slide) {
		const src = slide.getAttribute('data-src');
		if (src && !slide.style.backgroundImage) {
			slide.style.backgroundImage = `url(${src})`;
		}
	}

	function goToSlide(index) {
		if (isTransitioning) return;
		isTransitioning = true;

		slides[currentIndex].setAttribute('aria-hidden', 'true');
		currentIndex = (index + slides.length) % slides.length;
		const offset = -currentIndex * 100;
		slider.style.transform = `translateX(${offset}%)`;
		slides[currentIndex].setAttribute('aria-hidden', 'false');

		updateIndicators();
		loadImage(slides[currentIndex]);
		if (slides[currentIndex + 1]) loadImage(slides[currentIndex + 1]);
		if (slides[currentIndex - 1]) loadImage(slides[currentIndex - 1]);

		setTimeout(() => { isTransitioning = false; }, 500);
	}

	function nextSlide() {
		goToSlide(currentIndex + 1);
	}

	function prevSlide() {
		goToSlide(currentIndex - 1);
	}

	nextBtn.addEventListener('click', nextSlide);
	prevBtn.addEventListener('click', prevSlide);

	createIndicators();
	loadImage(slides[0]);

	let touchStartX = 0;
	let touchEndX = 0;

	slider.addEventListener('touchstart', e => {
		touchStartX = e.changedTouches[0].screenX;
	});

	slider.addEventListener('touchend', e => {
		touchEndX = e.changedTouches[0].screenX;
		if (touchStartX - touchEndX > 50) {
			nextSlide();
		} else if (touchEndX - touchStartX > 50) {
			prevSlide();
		}
	});

	function startAutoSlides() {
		autoSlideInterval = setInterval(nextSlide, 5000);
	}

	function stopAutoSlides() {
		clearInterval(autoSlideInterval);
	}

	slider.addEventListener('mouseenter', stopAutoSlides);
	slider.addEventListener('mouseleave', startAutoSlides);

	// 슬라이더가 존재할 경우에만 자동 슬라이드 시작
    if (slides.length > 0) {
        startAutoSlide();
    }
	// 책 슬라이더 관련 코드
	const bookSliderContainer = document.querySelector('.book-slider-container');
	const bookSlider = document.querySelector('.book-slider');
	const bookSlides = document.querySelectorAll('.book-slide');
	const bookPrevBtn = document.querySelector('.book-prev');
	const bookNextBtn = document.querySelector('.book-next');

	let currentBookIndex = 0;

	function goToBookSlide(index) {
		 if (isTransitioning || bookSlides.length === 0) return;
        isTransitioning = true;

        if (index < 0) {
            index = bookSlides.length - 1;
        } else if (index >= bookSlides.length) {
            index = 0;
        }
        currentBookIndex = index;
        const offset = -index * 100;
        bookSlider.style.transform = `translateX(${offset}%)`;

        setTimeout(() => { isTransitioning = false; }, 500);
    }

	function nextBookSlide() {
        goToBookSlide(currentBookIndex + 1);
    }

    function prevBookSlide() {
        goToBookSlide(currentBookIndex - 1);
    }

    if (bookPrevBtn && bookNextBtn) {
        bookNextBtn.addEventListener('click', nextBookSlide);
        bookPrevBtn.addEventListener('click', prevBookSlide);
    }

    // 책 슬라이더 터치 이벤트 처리
    let bookTouchStartX = 0;
    let bookTouchEndX = 0;

    bookSliderContainer.addEventListener('touchstart', e => {
        bookTouchStartX = e.changedTouches[0].screenX;
    }, { passive: true });

    bookSliderContainer.addEventListener('touchend', e => {
        bookTouchEndX = e.changedTouches[0].screenX;
        if (bookTouchStartX - bookTouchEndX > 50) {
            nextBookSlide();
        } else if (bookTouchEndX - bookTouchStartX > 50) {
            prevBookSlide();
        }
    });

    function startAutoSlide() {
        autoSlideInterval = setInterval(nextBookSlide, 5000);
    }

    function stopAutoSlide() {
        clearInterval(autoSlideInterval);
    }

    bookSliderContainer.addEventListener('mouseenter', stopAutoSlide);
    bookSliderContainer.addEventListener('mouseleave', startAutoSlide);

    // 슬라이더가 존재할 경우에만 자동 슬라이드 시작
    if (bookSlides.length > 0) {
        startAutoSlide();
    }
});