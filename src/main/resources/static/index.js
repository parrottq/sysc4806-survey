
//TODO
// 1. Get the post to create polls on server side - CHECK
// 2. Create + and - buttons to create questions (html elements)
// 3. Create js listeners for when a type is selected/changed
//     3.a Change dom to have the correct format for questions from the select
// 4. Add + and - for MC choices
// 5. ensure submit still works / tests

// Goes through the elements of a poll everywhere create survey and submits them via post to the backend
function createPoll() {
    let questions = [];
    let emptyArray = [];

    //Formats each question into a json format
    $('.question-container').each(function(index, element) {
        // console.log(JSON.stringify($(this)));
        // console.log(JSON.stringify($(this).find('select')));
        let question;
        if($(this).find('select').val() === "MultipleChoice") {

            //Get all question choices
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


function getTextQuestion() {
    return "<div class='question-container'><select class='question-type' name='question-type' onchange='changeQuestionType(this)'><option value='Text'>Text</option><option value='MultipleChoice'>Multiple Choice</option></select><button onClick='removeQuestion(this)' style='opacity: 0%'><i style='opacity: 0%' class='fa-solid fa-trash'></i></button><div class='question-title-container'><h2><input type='text' placeholder='Question Title'/></h2></div><div class='question-choice-container'></div><hr></div></div>";
}

function getChoiceQuestion() {
    return '<div class="question-container"><select class="question-type" name="question-type" onchange="changeQuestionType(this)"><option value="Text">Text</option><option selected value="MultipleChoice">Multiple Choice</option></select><button onClick="removeQuestion(this)" style="opacity: 0%"><i                             style="opacity:0%" class="fa-solid fa-trash"></i></button><div class="question-title-container"><h2><input class="question-title" type="text" placeholder="Question Title"/></h2></div><div class="question-choice-container"><div class="question-choice"><label><input type="text" placeholder="Choice"/></label><button onClick="removeChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-trash"></i></button><button onClick="addChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-plus"></i></button></div><div class="question-choice"><label><input type="text" placeholder="Choice"/></label><button onClick="removeChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-trash"></i></button><button onClick="addChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-plus"></i></button></div><div class="question-choice"><label><input type="text" placeholder="Choice"/></label><button onClick="removeChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-trash"></i></button><button onClick="addChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-plus"></i></button></div></div><hr></div>';
}

function getChoiceElement() {
    return '<div class="question-choice"><label><input type="text" placeholder="Choice"/></label><button onClick="removeChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-trash"></i></button><button onClick="addChoice(this)" style="opacity: 0%"><i                           style="opacity:0%" class="fa-solid fa-plus"></i></button></div>'
}

function generateUUID() {
    return this.crypto.randomUUID();
}

function addChoice(element) {
    $(element).parent().parent().append(getChoiceElement());
}

function removeQuestion(element) {
    $(element).parent().remove();
}

function removeChoice(element) {
    $(element).parent().remove();
}

function addQuestion(element) {

    $(".questions-container").append(getTextQuestion());
}

function changeQuestionType(element) {

    //Grab the dropdown value
    let choice = $(element).val();

    if(choice === "MultipleChoice") {
        $(element).parent().replaceWith(getChoiceQuestion());
    } else {
        $(element).parent().replaceWith(getTextQuestion());
    }
}



