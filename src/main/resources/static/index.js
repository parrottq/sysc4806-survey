
/**
 * Formats the input fields into a format acceptable for a Poll object
 */
function createPoll() {
    let questions = [];
    let emptyArray = [];

    if(!validateForm()) {
       alert("Please fill out all fields before submitting");
       return;
    }

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
            window.location.replace("/view-polls");
        },
        error: function() {
            alert("Error");
        }
    });
}

/**
 * Ensures that all fields on the survey are not empty
 */
function validateForm() {
    let isValid = true;

    //Check survey title
    if($('#survey-title').val() === "") {
        isValid = false;
        $('#survey-title').css("border-color", "red");
    }

    //Formats each question into a json format
    $('.question-container').each(function(index, element) {

        //Checks what kind of question it is (MC, Text, etc)
        if($(this).find('select').val() === "MultipleChoice") {

            //Format choices into array
            $('.question-choice').each(function(index, element) {
                //Check that each choice has a value
                if($(this).find('input').val() === "") {
                    $(this).find('input').css("border-color", "red");
                    isValid = false;
                }
            })

            //Check question title field
            if($(this).find('.question-title-container input').val() === "") {
                $(this).find('input').css("border-color", "red");
                isValid = false;
            }

        } else if ($(this).find('select').val() === "Text") {
            //Check question title field
            if($(this).find('.question-title-container input').val() === "") {
                $(this).find('input').css("border-color", "red");
                isValid = false;
            }
        } else {
            alert("Unknown element selected")
            isValid = false;
        }
    });

    return isValid;
}

/**
 * Returns a text question element in a question container
 * @returns {string} question container element
 */
function buildTextQuestion() {
    return "<div class='question-container'><select class='question-type' name='question-type' onchange='changeQuestionType(this)'><option value='Text'>Text</option><option value='MultipleChoice'>Multiple Choice</option></select><button onClick='removeQuestion(this)' style='opacity: 0%'><i style='opacity: 0%' class='fa-solid fa-trash'></i></button><div class='question-title-container'><h2><input type='text' placeholder='Question Title'/></h2></div><div class='question-choice-container'></div><hr></div></div>";
}
/**
 * Returns a multiple choice question element in a question container
 * @returns {string} question container element
 */
function buildChoiceQuestion() {
    return '<div class="question-container"><select class="question-type" name="question-type" onchange="changeQuestionType(this)"><option value="Text">Text</option><option selected value="MultipleChoice">Multiple Choice</option></select><button onClick="removeQuestion(this)" style="opacity: 0%"><i                             style="opacity:0%" class="fa-solid fa-trash"></i></button><div class="question-title-container"><h2><input class="question-title" type="text" placeholder="Question Title"/></h2></div><div class="question-choice-container"><div class="question-choice"><label><input type="text" placeholder="Choice"/></label><button onClick="removeChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-trash"></i></button><button onClick="addChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-plus"></i></button></div><div class="question-choice"><label><input type="text" placeholder="Choice"/></label><button onClick="removeChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-trash"></i></button><button onClick="addChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-plus"></i></button></div><div class="question-choice"><label><input type="text" placeholder="Choice"/></label><button onClick="removeChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-trash"></i></button><button onClick="addChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-plus"></i></button></div></div><hr></div>';
}
/**
 * Returns a multiple choice questions "choice" element
 * @returns {string} question choice container element
 */
function buildChoiceElement() {
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
    $(element).parent().parent().last().append(buildChoiceElement());
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

    $(".questions-container").last().append(buildTextQuestion());
}

/**
 * Changes the question elements from the container it was called from to the specified question type
 * @param element
 */
function changeQuestionType(element) {

    //Grab the dropdown value
    let choice = $(element).val();

    if(choice === "MultipleChoice") {
        $(element).parent().replaceWith(buildChoiceQuestion());
    } else {
        $(element).parent().replaceWith(buildTextQuestion());
    }
}

function getResults(clickEvent) {
    id = clickEvent.target.parentNode.querySelector('.poll_id').innerHTML.split(": ")[1];

    // Redirect to page
    window.location.assign('/view-polls/results?id=' + id);
}
