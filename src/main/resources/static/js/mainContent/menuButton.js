document.addEventListener('DOMContentLoaded', () => {
    const categoryButton = document.getElementById('categoryButton');
    const categoryDropdown = document.getElementById('categoryDropdown');
    const mainCategories = document.querySelector('.main-categories');
    const subCategories = document.getElementById('subCategories');

    const categoryData = {
        novel: {
            name: "소설",
            subcategories: {
                korean: {
                    name: "한국소설",
                    items: ["현대소설", "고전소설", "장편소설"]
                },
                foreign: {
                    name: "외국소설",
                    items: ["영미소설", "유럽소설", "일본소설"]
                }
            }
        },
        economy: {
            name: "경제/경영",
            subcategories: {
                economics: {
                    name: "경제",
                    items: ["경제이론", "경제사", "경제정책"]
                },
                management: {
                    name: "경영",
                    items: ["경영전략", "마케팅", "재무/회계"]
                }
            }
        },
        humanities: {
            name: "인문",
            subcategories: {
                philosophy: {
                    name: "철학",
                    items: ["서양철학", "동양철학", "윤리학"]
                },
                history: {
                    name: "역사",
                    items: ["세계사", "한국사", "문화사"]
                }
            }
        }
        // 추가 카테고리...
    };

    const toggleDropdown = async () => {
        return new Promise((resolve) => {
            categoryDropdown.classList.toggle('show');
            resolve();
        });
    };

    const updateSubCategories = (category) => {
        const data = categoryData[category];
        let html = `<h3>${data.name}</h3>`;
        for (const [subKey, subValue] of Object.entries(data.subcategories)) {
            html += `<h4>${subValue.name}</h4><ul>`;
            subValue.items.forEach(item => {
                html += `<li>${item}</li>`;
            });
            html += '</ul>';
        }
        subCategories.innerHTML = html;
    };

    categoryButton.addEventListener('click', async (event) => {
        event.stopPropagation();
        try {
            await toggleDropdown();
            console.log('드롭다운 토글 완료');
        } catch (error) {
            console.error('드롭다운 토글 중 오류 발생:', error);
        }
    });

    mainCategories.addEventListener('click', (event) => {
        const categoryBox = event.target.closest('.category-box');
        if (categoryBox) {
            const category = categoryBox.dataset.category;
            updateSubCategories(category);
            
            // 선택된 카테고리 강조 표시
            document.querySelectorAll('.category-box').forEach(box => {
                box.classList.remove('active');
            });
            categoryBox.classList.add('active');
        }
    });

    // 드롭다운 외부 클릭 시 닫기
    window.addEventListener('click', async (event) => {
        if (!event.target.closest('.dropdown') && categoryDropdown.classList.contains('show')) {
            try {
                await toggleDropdown();
                console.log('드롭다운 닫힘');
            } catch (error) {
                console.error('드롭다운 닫는 중 오류 발생:', error);
            }
        }
    });

    // 초기 서브카테고리 로드
    updateSubCategories('novel');
    document.querySelector('.category-box[data-category="novel"]').classList.add('active');
});