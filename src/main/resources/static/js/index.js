function create_UUID() {
    var dt = new Date().getTime();
    return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = (dt + Math.random() * 16) % 16 | 0;
        dt = Math.floor(dt / 16);
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
}

var form = document.getElementById('infoForm');
form.onsubmit = function(event){

    var xhr = new XMLHttpRequest();
    var formData = new FormData(form);
    //open the request
    xhr.open('POST','https://192.168.1.200/call/dispatch')
    xhr.setRequestHeader("Content-Type", "application/json");

    //send the form data
    let payload = JSON.parse(JSON.stringify(Object.fromEntries(formData)));
    payload.callerRoomKey = create_UUID();
    payload.callType = "XNTT";
    delete payload.id_front;
    delete payload.id_back;
    console.log(payload);
    xhr.send(JSON.stringify(payload));
    xhr.onreadystatechange = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            window.location.replace("call/room?id=" + payload.callerRoomKey);
        }
    }
    //Fail the onsubmit to avoid page refresh.
    return false;
}