// Get the modal
//import {AMQPWebSocketClient} from "./amqp-websocket-client.mjs";
console.log("true");
var modal = document.getElementById("callModal");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}
var stompClient = null;
var socket = new SockJS('/ws');
stompClient = Stomp.over(socket);
stompClient.connect({}, connectionSuccess);
function connectionSuccess() {
    console.log("connection success")
    stompClient.subscribe('/topic/' + window.location.pathname.substring(10), onMessageReceived);
}
// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    console.log(message);
    modal.style.display = "block";
}