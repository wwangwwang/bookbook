// 첫 번째 차트
var chartDom1 = document.getElementById('chart1');
var myChart1 = echarts.init(chartDom1);
var option1 = {

	tooltip: {
		trigger: 'item',
		formatter: '{b}: {c} ({d}%)'
	},
	legend: {
		orient: 'vertical',  // 범례를 수직으로 정렬
		left: '40%',  // 범례를 차트의 오른쪽에 위치
		top: 'center',  // 범례를 차트의 세로 가운데에 위치
		formatter: function(name) {
			var data = {
				'문학': '1위: 소설/시/희곡 > 한국소설',
				'사회과학': '2위: 사회과학 > 사회문제',
				'철학': '3위: 에세이 > 사진/그림 에세이',
				'어학': '4위: 소설/시/희곡 > 단편/중편소설',
				'예술': '5위: 소설/시/희곡 > 프랑스소설',
				'역사': '6위: 에세이 > 사진/그림 에세이'
			};
			return data[name];  // name 값에 따라 범례 항목의 내용을 설정
		}
	},
	series: [{
		//name: '예제 데이터',
		type: 'pie',
		radius: '50%',  // 원형 차트의 크기 설정
		center: ['20%', '50%'],
		label: {
			show: false, // 레이블을 숨김
			//position: 'left',
			//formatter: '{b}\n{d}%'  // 이름과 퍼센트를 표시
		},
		labelLine: {
			show: false // 레이블 라인을 숨김
		},
		data: [
			{ value: 5, name: '문학' },
			{ value: 20, name: '사회과학' },
			{ value: 15, name: '철학' },
			{ value: 10, name: '어학' },
			{ value: 10, name: '예술' },
			{ value: 20, name: '역사' }
		]
	}]
};
myChart1.setOption(option1);

// 두 번째 차트
var chartDom2 = document.getElementById('chart2');
var myChart2 = echarts.init(chartDom2);
var option2 = {

	tooltip: {},
	xAxis: {
		data: ['문학', '사회과학', '철학', '어학', '예술', '만화']
	},
	yAxis: {
		interval: 10,
	},
	series: [{
		//name: '예제 데이터',
		type: 'bar',
		data: [5, 20, 36, 10, 10, 50]
	}]
};
myChart2.setOption(option2);