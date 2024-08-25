document.getElementById('no-end-date').addEventListener('change', function() {
	const endDateInput = document.getElementById('event-end-date');
	if (this.checked) {
		endDateInput.disabled = true;
		endDateInput.value = '';
	} else {
		endDateInput.disabled = false;
	}
});