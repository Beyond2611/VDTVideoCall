let LOCAL_IP = "192.168.1.200";
let validDiv = document.getElementById("validDiv");
let invalidDiv = document.getElementById("invalidDiv");
let waitDiv = document.getElementById("invalidDiv");

var stompClient = null;
var socket = new SockJS('/ws');
window.onload = function (){
    const searchParams = new URLSearchParams(window.location.search);
    window.open("https://" + LOCAL_IP +"/call/room?id=" + searchParams.get("room"),'targetWindow','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=1200,height=800');
}
stompClient = Stomp.over(socket);
stompClient.connect({}, connectionSuccess);
function connectionSuccess() {
    console.log("connection success")
    stompClient.subscribe('/topic/' + window.location.search.substring(1), onMessageReceived);
}

var message;
function onMessageReceived(payload) {
    message = JSON.parse(payload.body);
    console.log(message);
    if(message.isValid === "Valid")
        window.location.replace("/response/success")
    else window.location.replace("/response/failed")
}