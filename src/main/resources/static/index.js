
// Generates a UUID in the format expected by the backend
function generateUUID() {
    return this.crypto.randomUUID();
}


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

    let o = {
        "poll": poll
    }
    console.log(JSON.stringify(o));

    //Send via ajax
    $.post('/save-poll', {
        poll: JSON.stringify(o)
    }, function(data) {
        var json = JSON.parse(data);


    }).done(function() {
    }).fail(function(xhr, textStatus, errorThrown) {
        alert(errorThrown);
    }).complete(function() {
        alert("Complete");
    });
    // $.ajax({
    //     type: 'POST',
    //     url: '/save-poll',
    //     data: { 'poll': poll },
    //     // dataType: "json",
    //     // contentType: "application/json; charset=utf-8",
    //     success: function(text) {
    //         alert("Success");
    //     },
    //     error: function() {
    //         alert("Error");
    //     }



}

