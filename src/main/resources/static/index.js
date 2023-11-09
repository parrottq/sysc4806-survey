
// Generates a UUID in the format expected by the backend
function generateUUID() {
    return this.crypto.randomUUID();
}
//TODO
// 1. Get the post to create polls on server side
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

