function displayStars() {
    const starElements = document.querySelectorAll('.star-rating');
    starElements.forEach(element => {
        const rating = parseInt(element.dataset.rating);
        const stars = '★'.repeat(rating) + '☆'.repeat(5 - rating);
        element.textContent = stars;
    });
}

document.addEventListener('DOMContentLoaded', displayStars);