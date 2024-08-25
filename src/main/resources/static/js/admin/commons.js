document.addEventListener('DOMContentLoaded', function() {
    const selectAllCheckbox = document.getElementById('select-all');
    const itemCheckboxes = document.querySelectorAll('.select-item');

    if (selectAllCheckbox) { // 요소가 존재하는지 확인
        selectAllCheckbox.addEventListener('change', function() {
            itemCheckboxes.forEach(checkbox => {
                checkbox.checked = selectAllCheckbox.checked;
            });
        });
    }
});

//리스트 페이지 이동 버튼
document.addEventListener('DOMContentLoaded', function() {
	const itemsPerPage = 15;
	const tableBody = document.querySelector('tbody');
	const paginationContainer = document.getElementById('pagination');
	let currentPage = 1;
	let rows = Array.from(tableBody.querySelectorAll('tr'));
	let pageCount = Math.ceil(rows.length / itemsPerPage);

	function displayList(items, wrapper, rowsPerPage, page) {
		wrapper.innerHTML = "";
		page--;

		let start = rowsPerPage * page;
		let end = start + rowsPerPage;
		let paginatedItems = items.slice(start, end);

		for (let i = 0; i < paginatedItems.length; i++) {
			wrapper.appendChild(paginatedItems[i]);
		}
	}

	function setupPagination(items, wrapper, rowsPerPage) {
		wrapper.innerHTML = "";

		let maxPagesToShow = 10;
		let startPage = Math.max(1, currentPage - Math.floor(maxPagesToShow / 2));
		let endPage = Math.min(pageCount, startPage + maxPagesToShow - 1);

		startPage = Math.max(1, endPage - maxPagesToShow + 1)
		wrapper.appendChild(createButton('<<', () => goToPage(1)));
		wrapper.appendChild(createButton('<', () => goToPage(Math.max(1, currentPage - 1))));

		for (let i = startPage; i <= endPage; i++) {
			let btn = createButton(i, () => goToPage(i));
			if (currentPage == i) btn.classList.add('active');
			wrapper.appendChild(btn);
		}
		wrapper.appendChild(createButton('>', () => goToPage(Math.min(pageCount, currentPage + 1))));
		wrapper.appendChild(createButton('>>', () => goToPage(pageCount)));
	}
	function createButton(text, onClick) {
		let button = document.createElement('button');

		button.innerText = text;
		button.addEventListener('click', onClick);

		return button;
	}
	function goToPage(page) {
		currentPage = page;
		displayList(rows, tableBody, itemsPerPage, currentPage);
		setupPagination(rows, paginationContainer, itemsPerPage);
	}
	displayList(rows, tableBody, itemsPerPage, currentPage);
	setupPagination(rows, paginationContainer, itemsPerPage);
});


