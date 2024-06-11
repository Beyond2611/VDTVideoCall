var form = document.getElementById('login-form');
form.onsubmit = function(event){
    console.log("Sending Form");
    var xhr = new XMLHttpRequest();
    var formData = new FormData(form);
    //open the request
    xhr.open('POST','http://localhost:8080/auth/login')
    xhr.setRequestHeader("Content-Type", "application/json");

    //send the form data
    xhr.send(JSON.stringify(Object.fromEntries(formData)));

    xhr.onreadystatechange = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            form.reset(); //reset form after AJAX success or do something else
        }
    }
    //Fail the onsubmit to avoid page refresh.
    return false;
}