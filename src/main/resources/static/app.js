// The main pieces of this JavaScript file to understand are the stompClient.onConnect and sendName functions.
//
// stompClient is initialized with brokerURL referring to path /gs-guide-websocket, which is where our websockets server waits for connections. Upon a successful connection, the client subscribes to the /topic/greetings destination, where the server will publish greeting messages. When a greeting is received on that destination, it will append a paragraph element to the DOM to display the greeting message.
//
// The sendName() function retrieves the name entered by the user and uses the STOMP client to send it to the /app/hello destination (where GreetingController.greeting() will receive it).
//
// The main.css can be omitted if you like, or you can create an empty one, just so the <link> can be resolved.

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:1999/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'name': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});