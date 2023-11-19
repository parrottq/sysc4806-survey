let socket = new WebSocket("/poll/results-stream");


socket.onopen = function(e) {
    console.log("[open] Connection Established")
    socket.send("Subscribe")
}

socket.onmessage = function(event) {
    console.log(`Message Received: ${event.data}`);
}
