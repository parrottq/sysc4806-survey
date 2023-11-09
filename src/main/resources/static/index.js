
// Generates a UUID in the format expected by the backend
function generateUUID() {
    return this.crypto.randomUUID();
}

// Goes through the elements of a poll everywhere create survey and submits them via post to the backend
function createPoll() {
    alert("It works!");
    let questions = [];
    //Formats each question into a json format
    $('.question-container').each(function(index, element) {
        let question;
        if(element.find('select').val() === "MultipleChoice") {
            // question = {
            //     "id" : generateUUID(),
            //     ""
            // }
        } else if (element.find('select').val() === "Text") {
            

        } else {
            alert("Unknown element selected")
        }


        // let question = {
        //     "id"    : generateUUID(),
        //     "title" : element.find('.question-title'),
        //
        // }
        questions.push(question);
    });


}

