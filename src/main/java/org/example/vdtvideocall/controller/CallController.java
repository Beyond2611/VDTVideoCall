package org.example.vdtvideocall.controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.example.vdtvideocall.model.CallInfo;
import org.example.vdtvideocall.payload.request.CallEndedRequest;
import org.example.vdtvideocall.payload.request.CallRequest;
import org.example.vdtvideocall.payload.response.CallEndedResponse;
import org.example.vdtvideocall.payload.response.CallResponse;
import org.example.vdtvideocall.repositories.CallRepository;
import org.example.vdtvideocall.services.CallHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "192.168.1.3:8000")
@Controller
@RequestMapping("/call")
public class CallController {
    private final SimpMessageSendingOperations messagingTemplate;
    private CallHandlerService callHandlerService;
    private CallRepository callRepository;
    @Autowired
    public CallController(CallHandlerService callHandlerService, SimpMessageSendingOperations messagingTemplate, CallRepository callRepository) {
        this.messagingTemplate = messagingTemplate;
        this.callHandlerService = callHandlerService;
        this.callRepository = callRepository;
    }

    @PostMapping("/dispatch")
    public ResponseEntity<CallResponse> handleCall(@Valid @RequestBody CallRequest callRequest) {
        CallResponse response = callHandlerService.queueCall(callRequest);
        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Location", "/call/room?id=" + callRequest.getCallerRoomKey());
        System.out.println(response);
        //return "redirect:/call/room?id=" + callRequest.getCallerRoomKey();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/endcall/{callInfoId}")
    public ResponseEntity<CallEndedResponse> endCall(@PathVariable String callInfoId, @RequestBody CallEndedRequest callEndedRequest) {
        messagingTemplate.convertAndSend("/topic/room=" + callEndedRequest.getRoomKey(), callEndedRequest);
        return ResponseEntity.ok(callHandlerService.endCall(callInfoId));
    }
    @GetMapping("/room")
    public String callPopup(@RequestParam String id){
        return "callPopup";
    }
}
