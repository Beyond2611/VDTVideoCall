// Get the modal
let LOCAL_IP = "192.168.1.200";
//import {AMQPWebSocketClient} from "./amqp-websocket-client.mjs";
console.log("true");
var modal = document.getElementById("callModal");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

let dataContainer = document.getElementById("data-container")
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
    document.getElementById("callName").innerHTML = message.id;
    let userInfo = JSON.parse(payload.body);
    delete userInfo.callType;
    delete userInfo.callerRoomKey;
    delete userInfo.callId;
    console.log(message);
    var dataTable = document.getElementById("validTable");
    let curRow = 0;
    let row = dataTable.rows;
    for (var i = 0; i < Object.keys(userInfo).length; i++) {
        var x = row[curRow].insertCell(-1);
        x.innerHTML = Object.values(userInfo)[i];
        curRow++;
    }
    dataContainer.style.display = "flex";
    modal.style.display = "block";
}
ansButton.onclick = function (){
    window.open("https://" + LOCAL_IP +"/call/room?id=" + message.callerRoomKey,'targetWindow','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=1000,height=600');
    modal.style.display = "none";
}
function clearInfo()
{
    var dataTable = document.getElementById("validTable");
    let curRow = 0;
    let row = dataTable.rows;
    for (var i = 0; i < row.length; i++) {
        row[curRow].deleteCell(-1);
        curRow++;
    }
    dataContainer.style.display = "none";
}
let validButton = document.getElementById("valid-btn");
validButton.onclick = function (){
    clearInfo();
    let xhr = new XMLHttpRequest();
    xhr.open('POST',"https://"+ LOCAL_IP + "/call/endcall/" + message.callId);
    xhr.setRequestHeader("Content-Type", "application/json");
    let payload = {};
    payload.callId = message.callId;
    payload.roomKey = message.callerRoomKey;
    payload.isValid = "Valid";
    xhr.send(JSON.stringify(payload));
}
let invalidButton = document.getElementById("invalid-btn");
invalidButton.onclick = function (){
    clearInfo();
    let xhr = new XMLHttpRequest();
    xhr.open('POST',"https://"+ LOCAL_IP +"/call/endcall/"+ message.callId)
    xhr.setRequestHeader("Content-Type", "application/json");
    let payload = {};
    payload.callId = message.callId;
    payload.roomKey = message.callerRoomKey;
    payload.isValid = "Invalid";
    xhr.send(JSON.stringify(payload));
}
