var ctx = document.getElementById('progressChart').getContext('2d');

const progress = document.getElementById('progress').getAttribute('value');
const remain = 100 - progress;

var data = {
    datasets: [{
        data: [progress, remain], // 데이터: [진행률, 남은 부분]
        backgroundColor: ['#E76161', '#dddddd'], // 진행률 색상과 남은 부분의 색상
        borderWidth: 0 // 테두리 너비
    }],
};

var options = {
    cutout: '50%', // 중앙 부분 빈 공간의 크기
    tooltips: { enabled: false }, // 툴팁 비활성화
    responsive: false, // 반응형 비활성화
    maintainAspectRatio: false // 가로 세로 비율 유지 비활성화
};

new Chart(ctx, {
    type: 'doughnut', // 원형 그래프 유형
    data: data,
    options: options
});