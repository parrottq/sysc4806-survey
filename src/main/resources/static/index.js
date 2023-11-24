/**
 * Saves multiple choice and text answers in answer repository
 *
 */
function saveAnswers() {
    let questions = [];
    let pollID = document.getElementById("poll-id").getAttribute('data-value');

    $('.multiple-choice').each(function() {
        let answers = [];
        let question;
        document.querySelectorAll('.multiple-choice .choice').forEach(function(selectedInput) {
            if (selectedInput.checked) {
                let answer = {
                    "id" : generateUUID(),
                    "answerChoice" : selectedInput.value
                }
                answers.push(answer)
                
                question = {
                    "id" : selectedInput.getAttribute('data-name'),
                    "title" : " ",
                    "questionType" : null,
                    "possibleChoices" : [],
                    "answers" : answers
                }
                questions.push(question)
            }
        });
    });

    document.querySelectorAll('.textfield-question input').forEach(function(textAnswer){
        let question;
        let answer = {
            "id" : generateUUID(),
            "answerChoice" : textAnswer.value
        }
        question = {
            "id" : textAnswer.getAttribute('data-name'),
            "title" : " ",
            "questionType" : null,
            "possibleChoices" : [],
            "answers" : [ answer ]
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
 * alerts the user that the poll they are trying to access is currently closed
 */
function notifyUserOfClosedStatus(){
    alert("This poll is closed.")
}

/**
 * Redicrects the display page to the desired poll given the ID
 * @param id
 */
function redirectToPollResults(id){
    window.location.assign("/current-poll/results?id=" + id);
}

/**
 * returns current page to the home page
 */
function returnToHomePage(){
    window.location.assign("/");
}

/**
 * returns current page to the poll display page
 */
function displayPolls(){
    window.location.assign("/display-polls");
}

function getResults(clickEvent) {
    id = clickEvent.target.parentNode.querySelector('.poll_id').innerHTML.split(": ")[1];
  
    // Redirect to page
    window.location.assign('/view-polls/results?id=' + id);
}