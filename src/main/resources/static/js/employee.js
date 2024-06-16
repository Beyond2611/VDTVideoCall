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

var ansButton = document.getElementById("ansBtn");
var stompClient = null;
var socket = new SockJS('/ws');
stompClient = Stomp.over(socket);
stompClient.connect({}, connectionSuccess);
function connectionSuccess() {
    console.log("connection success")
    stompClient.subscribe('/topic/' + window.location.search.substring(1), onMessageReceived);
}
// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
var message;
function onMessageReceived(payload) {
    message = JSON.parse(payload.body);
    document.getElementById("callName").innerHTML = message.Id;
    console.log(message);
    modal.style.display = "block";
}

ansButton.onclick = function (){
    window.open('https://192.168.1.200/call/room?id=' + message.roomKey,'targetWindow','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=1000,height=600');
    modal.style.display = "none";
}