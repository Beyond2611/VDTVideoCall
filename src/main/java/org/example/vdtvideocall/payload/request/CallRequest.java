package org.example.vdtvideocall.payload.request;

import lombok.Data;

@Data
public class CallRequest {
    private String callType;
    private String callerRoomKey;
    private String name;
    private String phone;
    private String id_num;
    private String origin;
    private String birthplace;
    private String home;
}
