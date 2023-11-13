
//TODO
// 1. Controller Tests

/**
 * Formats the input fields into a format acceptable for a Poll object
 */
function createPoll() {
    let questions = [];
    let emptyArray = [];

    //Formats each question into a json format
    $('.question-container').each(function(index, element) {

        let question;
        //Checks what kind of question it is (MC, Text, etc)
        if($(this).find('select').val() === "MultipleChoice") {

            //Format choices into array
            let choices = [];
            $('.question-choice').each(function(index, element) {
                choices.push($(this).find('input').val());
            })

            question = {
                "id" : generateUUID(),
                "title" : $(this).find('.question-title-container input').val(),
                "questionType" : $(this).find('select').val(),
                "possibleChoices" : choices,
                "answers" : emptyArray
            }

        } else if ($(this).find('select').val() === "Text") {
            question = {
                "id" : generateUUID(),
                "title" : $(this).find('.question-title-container input').val(),
                "questionType" : $(this).find('select').val(),
                "possibleChoices" : emptyArray,
                "answer" : emptyArray

            }
        } else {
            alert("Unknown element selected")
        }
        questions.push(question);
    });

    //Format into poll object
    let poll = {
        "id" : generateUUID(),
        "isClosed" : false,
        "title" : $('#survey-title').val(),
        "questions" : questions
    }

    //Send to the backend
    $.ajax({
        type: 'POST',
        url: '/save-poll',
        data: JSON.stringify(poll),
        contentType: "application/json; charset=utf-8",
        success: function(text) {
            alert("Success");
        },
        error: function() {
            alert("Error");
        }
    });
}

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
        console.log(pollID)
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
 * Returns a text question element in a question container
 * @returns {string} question container element
 */
function getTextQuestion() {
    return "<div class='question-container'><select class='question-type' name='question-type' onchange='changeQuestionType(this)'><option value='Text'>Text</option><option value='MultipleChoice'>Multiple Choice</option></select><button onClick='removeQuestion(this)' style='opacity: 0%'><i style='opacity: 0%' class='fa-solid fa-trash'></i></button><div class='question-title-container'><h2><input type='text' placeholder='Question Title'/></h2></div><div class='question-choice-container'></div><hr></div></div>";
}
/**
 * Returns a multiple choice question element in a question container
 * @returns {string} question container element
 */
function getChoiceQuestion() {
    return '<div class="question-container"><select class="question-type" name="question-type" onchange="changeQuestionType(this)"><option value="Text">Text</option><option selected value="MultipleChoice">Multiple Choice</option></select><button onClick="removeQuestion(this)" style="opacity: 0%"><i                             style="opacity:0%" class="fa-solid fa-trash"></i></button><div class="question-title-container"><h2><input class="question-title" type="text" placeholder="Question Title"/></h2></div><div class="question-choice-container"><div class="question-choice"><label><input type="text" placeholder="Choice"/></label><button onClick="removeChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-trash"></i></button><button onClick="addChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-plus"></i></button></div><div class="question-choice"><label><input type="text" placeholder="Choice"/></label><button onClick="removeChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-trash"></i></button><button onClick="addChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-plus"></i></button></div><div class="question-choice"><label><input type="text" placeholder="Choice"/></label><button onClick="removeChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-trash"></i></button><button onClick="addChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-plus"></i></button></div></div><hr></div>';
}
/**
 * Returns a multiple choice questions "choice" element
 * @returns {string} question choice container element
 */
function getChoiceElement() {
    return '<div class="question-choice"><label><input type="text" placeholder="Choice"/></label><button onClick="removeChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-trash"></i></button><button onClick="addChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-plus"></i></button></div>'
}

/**
 * Generates a UUID in the format the backend expects
 * @returns {`${string}-${string}-${string}-${string}-${string}`}
 */
function generateUUID() {
    return this.crypto.randomUUID();
}

/**
 * Adds a choice element to the button it was called from
 * @param element
 */
function addChoice(element) {
    $(element).parent().parent().append(getChoiceElement());
}

/**
 * Removes a question element and container from the button it was called from
 * @param element
 */
function removeQuestion(element) {
    $(element).parent().remove();
}

/**
 * Removes a choice element and container from the button it was called from
 * @param element
 */
function removeChoice(element) {
    $(element).parent().remove();
}

/**
 * Adds a question element and container to the button it was called from
 * @param element
 */
function addQuestion(element) {

    $(".questions-container").append(getTextQuestion());
}

/**
 * Changes the question elements from the container it was called from to the specified question type
 * @param element
 */
function changeQuestionType(element) {

    //Grab the dropdown value
    let choice = $(element).val();

    if(choice === "MultipleChoice") {
        $(element).parent().replaceWith(getChoiceQuestion());
    } else {
        $(element).parent().replaceWith(getTextQuestion());
    }
}

/**
 * Redicrects the display page to the desired poll given the ID
 * @param id
 */
function redirectToPoll(id){
    // console.log(id);
    location.href =  "/current-poll/" + id;
}

function getResults(clickEvent) {
    id = clickEvent.target.parentNode.querySelector('.poll_id').innerHTML.split(": ")[1];
  
    // Redirect to page
    window.location.assign('/view-polls/results?id=' + id);
}
