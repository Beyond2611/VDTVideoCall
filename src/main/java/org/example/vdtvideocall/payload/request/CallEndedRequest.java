package org.example.vdtvideocall.payload.request;

import lombok.Data;

@Data
public class CallEndedRequest {
    private String callId;
    private String roomKey;
    private String isValid;
}
