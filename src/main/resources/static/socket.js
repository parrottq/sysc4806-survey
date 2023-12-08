let socket;

function subscribe(pollId) {
    socket = new WebSocket("wss:" + window.location.host + "/poll/results-stream");

    socket.onerror = function (event) {
        socket = new WebSocket("ws:" + window.location.host + "/poll/results-stream");
        socket_callbacks(pollId)
    }

    socket_callbacks(pollId)
}

function socket_callbacks(pollId) {
    socket.onopen = function (e) {
        socket.send(JSON.stringify({ "type": "Subscribe", "pollId": pollId }))
        console.log("[open] Connection Established")
    }

    socket.onmessage = function (event) {
        console.log(`Message Received: ${event.data}`);
        let questions = JSON.parse(event.data)["questions"];
        let docQuestions;
        try {
            docQuestions = Array.from(document.querySelectorAll('[id=question]'));
        } catch (err) {
            docQuestions = []
        }
        for (let i = 0; i < docQuestions.length; i++) {
            let question = questions[i];
            let answer = Array.from(docQuestions[i].querySelectorAll("[id=answers]"));
            answer[0].replaceChildren([]);
            let elem = document.createElement("div");
            for (let w = 0; w < questions[i].answers.length; w++) {
                let child = document.createElement("p");
                $(child).text(questions[i].answers[w].answerChoice).val();
                elem.appendChild(child);
            }
            answer[0].appendChild(elem);

            if (question.questionType === 'MultipleChoice') {

                let chart = Highcharts.charts.find((c) => c.renderTo.id === 'pie-chart-' + question.id);
                if (chart) {
                    updateChartData(chart, question);

                } else {
                    console.error('No chart found for question with id ' + question.id);
                }
            }
        }
    }
}

