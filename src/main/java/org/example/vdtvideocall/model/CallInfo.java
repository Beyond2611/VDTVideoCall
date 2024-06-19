package org.example.vdtvideocall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vdtvideocall.payload.request.CallRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Calls")
public class CallInfo {
    @Id
    private String callId;
    private String callType;
    private String employeeId;
    private String callStatus;
    private Instant receivedAt;
    private Instant startedAt;
    private Instant endedAt;
    private String roomKey;
    private boolean inProgress;
    private boolean isQueued;
    private CallRequest callRequest;
}
