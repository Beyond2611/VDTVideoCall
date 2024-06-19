## Video Call Center using Spring Boot, RabbitMQ, WebRTC, SocketIO.
---
* Clone this repository: 
```
git clone https://github.com/Beyond2611/VDTVideoCall.git
```
* Get your local ip address with ` ipconfig ` or `ifconfig`. Ex: `192.168.1.200`
* Change the `LOCAL_IP` and `YOUR_LOCAL_IP` in the `.js` files in `src/main/java/org/example/vdtvideocall/resources/static/js` to your local ip address.
* Change the ip address in `nginx.conf` to your local ip address.
* Build the `nginx` and the `vdt/videocall` image:
```
docker build -t vdt/videocall .;
docker build -t nginx ./nginx;
```
* Run `docker-compose up`.
---
* Create an employee through postman with `employee/create`
![image](https://github.com/Beyond2611/VDTVideoCall/assets/91667524/4d4b1bbf-bfbc-485b-b686-9c52b74b90fa)
* Use the employee's id to access: `employee?id=<employee id>`
* On another tab/device, enter your info on `/`. After sending the form, a pop up will open joining a call room.
* On the employee's page, confirm joining the call to join.


 
