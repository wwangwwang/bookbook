// 기존 코드
function btnDeleteClicked() {
	return confirm("정말로 삭제하시겠습니까?");
}

// 검색 기능 추가
document.addEventListener('DOMContentLoaded', function() {
	const searchBtn = document.querySelector('.search-btn');
	const searchInput = document.querySelector('input[type="text"]');
	const searchSelect = document.getElementById('search');
	const tableRows = document.querySelectorAll('tbody tr');

	searchBtn.addEventListener('click', performSearch);

	function performSearch() {
		const searchTerm = searchInput.value.toLowerCase().trim();
		const searchCategory = searchSelect.value;

		tableRows.forEach(row => {
			let cellToSearch;
			switch (searchCategory) {
				case '상품명':
					cellToSearch = row.cells[2]; // 제목 열
					break;
				case '상품코드':
					cellToSearch = row.cells[1]; // 도서번호 열
					break;
				case '출판사':
					cellToSearch = row.cells[5]; // 출판사 열
					break;
				case '출판사':
					cellToSearch = row.cells[4]; // 저자 열
					break;
				default:
					cellToSearch = row.cells[2]; // 기본값은 제목 열
			}

			const cellText = cellToSearch.textContent.toLowerCase();

			if (cellText.includes(searchTerm)) {
				row.style.display = '';
			} else {
				row.style.display = 'none';
			}
		});
	}
});
