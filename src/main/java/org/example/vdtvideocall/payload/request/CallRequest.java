package org.example.vdtvideocall.payload.request;

import lombok.Data;

@Data
public class CallRequest {
    private String callType;
    private String callerRoomKey;
}
