package org.example.vdtvideocall.controller;

import jakarta.validation.Valid;
import org.example.vdtvideocall.payload.request.CallRequest;
import org.example.vdtvideocall.payload.response.CallEndedResponse;
import org.example.vdtvideocall.payload.response.CallResponse;
import org.example.vdtvideocall.services.CallHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/call")
public class CallController {
    private CallHandlerService callHandlerService;
    @Autowired
    public CallController(CallHandlerService callHandlerService) {
        this.callHandlerService = callHandlerService;
    }

    @PostMapping("/dispatch")
    public ResponseEntity<CallResponse> handleCall(@Valid @RequestBody CallRequest callRequest) {
        CallResponse response = callHandlerService.queueCall(callRequest);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/endcall/{callInfoId}")
    public ResponseEntity<CallEndedResponse> endCall(@PathVariable String callInfoId) {
        return ResponseEntity.ok(callHandlerService.endCall(callInfoId));
    }
}
