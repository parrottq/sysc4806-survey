//On Page load, grab the html elements and store them for use later
let textQuestion = "";
let multipleChoiceQuestion = "";
let choiceOption = "";

$(document).ready(function() {
    //Grab elements on page
    $('.question-container').each(function(index, element) {
        if($(this).find('select').val() === "MultipleChoice") {
            multipleChoiceQuestion = this.outerHTML;
            choiceOption = $(this).find('.question-choice').first()[0].outerHTML;
        } else if ($(this).find('select').val() === "Text") {
            textQuestion = this.outerHTML;
        } else {
            console.log("Unknown element found");
        }
    })

});

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
            $(this).find('.question-choice').each(function(index, element) {
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



//////////


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
    $(element).parent().parent().append(choiceOption);
}

/**
 * Removes a question element and container from the button it was called from
 * @param element
 */
function removeQuestion(element) {
    //Check if this is the last question on the page or not.
    if($(element).parent().parent().children().length === 1) return;

    $(element).parent().remove();
}

/**
 * Removes a choice element and container from the button it was called from
 * @param element
 */
function removeChoice(element) {
    //Check if this is the last question on the page or not.
    if($(element).parent().parent().children().length === 1) return;

    $(element).parent().remove();
}

/**
 * Adds a question element and container to the button it was called from
 * @param element
 */
function addQuestion(element) {

    $(".questions-container").append(textQuestion);
}

/**
 * Changes the question elements from the container it was called from to the specified question type
 * @param element
 */
function changeQuestionType(element) {

    //Grab the dropdown value
    let choice = $(element).val();

    if(choice === "MultipleChoice") {
        $(element).parent().replaceWith(multipleChoiceQuestion);
    } else {
        $(element).parent().replaceWith(textQuestion);
    }
}