/**
 * Saves multiple choice and text answers in answer repository
 *
 */
function saveAnswers() {
    let questions = [];
    let answers = [];
    let answer;
    let emptyArray = [];
    let pollID = document.getElementById("poll-id").getAttribute('data-value');

    $('.multiple-choice').each(function(index, element) {
        let question;
        document.querySelectorAll('.multiple-choice .choice').forEach(function(selectedInput) {
            if (selectedInput.checked) {
                answer = {
                    "id" : generateUUID(),
                    "answerChoice" : selectedInput.value
                }
                answers.push(answer)
                question = {
                    "id" : selectedInput.getAttribute('data-name'),
                    "title" : " ",
                    "questionType" : null,
                    "possibleChoices" : emptyArray,
                    "answers" : answers
                }
                questions.push(question)
            }
        });
    });

    document.querySelectorAll('.textfield-question input').forEach(function(textAnswer){
        let question;
        answer = {
            "id" : generateUUID(),
            "answerChoice" : textAnswer.value
        }
        answers.push(answer)
        question = {
            "id" : textAnswer.getAttribute('data-name'),
            "title" : " ",
            "questionType" : null,
            "possibleChoices" : emptyArray,
            "answers" : answers
        }
        questions.push(question)
    });


    let poll = {
        "id" : pollID,
        "isClosed" : false,
        "title" : "",
        "questions" : questions
    }

    $.ajax({
        type: 'POST',
        url: '/save-form',
        data: JSON.stringify(poll),
        contentType: "application/json; charset=utf-8",
        success: function(text) {
            alert("Poll has been submitted");
        },
        error: function() {
            alert("Error");
        }
    });

}

/**
 * Redicrects the display page to the desired poll given the ID
 * @param id
 */
function redirectToPoll(id){
    location.href =  "/current-poll/" + id;
}

/**
 * returns current page to the home page
 */
function returnToHomePage(){
    location.href = "http://localhost:8080";
}

/**
 * returns current page to the poll display page
 */
function displayPolls(){
    location.href = "http://localhost:8080/display-polls";
}

function getResults(clickEvent) {
    id = clickEvent.target.parentNode.querySelector('.poll_id').innerHTML.split(": ")[1];
  
    // Redirect to page
    window.location.assign('/view-polls/results?id=' + id);
}