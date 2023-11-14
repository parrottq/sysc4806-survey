
function getResults(clickEvent) {
    id = clickEvent.target.parentNode.querySelector('.poll_id').innerHTML.split(": ")[1];

    // Redirect to page
    window.location.assign('/view-polls/results?id=' + id);
}
