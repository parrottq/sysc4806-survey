/**
 * Saves multiple choice and text answers in answer repository
 *
 */
function saveAnswers() {
    let questions = [];
    let pollID = document.getElementById("poll-id").getAttribute('data-value');

    document.querySelectorAll('.multiple-choice .choice').forEach(function(selectedInput) {
        let answers = [];
        let question;

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
            Swal.fire({
                title: "Your answers have been saved!",
                icon: "sucess"
            }).then((result) => {
                window.location.href = "/display-polls"
            })

        },
        error: function(xhr, status, error) {
            Swal.fire({
                title: "Failed to submit poll",
                text: xhr.responseText,
                icon: "error"
            });

        }
    });
}

/**
 * Redirects the display page to the desired poll given the ID
 * @param id
 */
function redirectToPoll(id){
    location.href =  "/current-poll/" + id;
}

/**
 * closes the current poll, and alerts the backend to change status of poll
 */
function closePoll(button){
    pollId = $(button).attr("data-name")

    $.ajax({
        type: 'POST',
        url: '/display-polls',
        data: JSON.stringify(pollId),
        contentType: "application/json; charset=utf-8"
    });

    // On next reload, the dom will reflect this anyways
    $(button).parent().find(".view-poll-button").hide()
    $(button).parent().parent().find(".status").text("Status: Closed")
    $(button).hide()
    $('#view').hide()
}

/**
 * Redirects the display page to the desired poll given the ID
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