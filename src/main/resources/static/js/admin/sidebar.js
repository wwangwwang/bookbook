document.addEventListener('DOMContentLoaded', () => {
	const toggleSubmenu = (event) => {
		const menuItem = event.currentTarget;
		const submenu = menuItem.nextElementSibling;

		// 드라이브 링크 처리
		if (menuItem.getAttribute('href') && menuItem.getAttribute('href').includes('auth.worksmobile.com')) {
			return; // 기본 링크 동작 허용
		}

		event.preventDefault();

		if (submenu && submenu.classList.contains('submenu')) {
			const isHidden = window.getComputedStyle(submenu).display === 'none';

			// 다른 모든 서브메뉴 닫기
			document.querySelectorAll('.submenu').forEach(sub => {
				if (sub !== submenu) {
					sub.style.display = 'none';
				}
			});

			// 현재 서브메뉴 토글
			submenu.style.display = isHidden ? 'block' : 'none';

			// 활성 클래스 토글
			menuItem.classList.toggle('active', isHidden);
		}
	};

	const menuItems = document.querySelectorAll('.menu');
	menuItems.forEach(item => {
		item.addEventListener('click', toggleSubmenu);
	});

	// 페이지 로드 시 현재 페이지에 해당하는 메뉴 항목 활성화
	const currentPath = window.location.pathname;
	document.querySelectorAll('.submenu a').forEach(link => {
		if (link.getAttribute('href') === currentPath) {
			link.classList.add('active');
			const parentMenu = link.closest('li').previousElementSibling;
			if (parentMenu && parentMenu.classList.contains('menu')) {
				parentMenu.classList.add('active');
				link.closest('.submenu').style.display = 'block';
			}
		}
	});
});