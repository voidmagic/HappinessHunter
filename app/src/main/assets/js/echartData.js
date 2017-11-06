
function processRaw(rawData) {
    var dataPairs = rawData.split(',')
    var postData = new Array();
    var i;
    for (i in dataPairs) {
        var score = dataPairs[i].split(':');
        postDataPair = new Object();
        postDataPair.name = score[0]
        postDataPair.value = parseInt(score[1])
        postData.push(postDataPair)
    }

    return postData;
}

function drawWordCloud(rawData) {
    var myChart = echarts.init(document.getElementById('main'));
    option = {
        tooltip: {
            show: false
        },

        series: [{
            name: 'HappinessWordCloud',
            type: 'wordCloud',
            shape: 'circle',
            textRotation : [0, 45, 90, -45],
            sizeRange: [20, 110],
            width: '80%',
            height: '80%',
            textStyle: {
                normal: {
                    color: function() {
                        return 'rgb(' + [
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160)
                        ].join(',') + ')';
                    }
                },
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            data: processRaw(rawData)
        }]
    };

    myChart.setOption(option);
    myChart.on('click', function (params) {window.android.wordClicked(params.name)});
}




