package org.example.vdtvideocall.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallRequest {
    private String callType;
    private String callerRoomKey;
    private String callId;
    private String name;
    private String phone;
    private String id_num;
    private String origin;
    private String birthplace;
    private String brithdate;
    private String home;
}
