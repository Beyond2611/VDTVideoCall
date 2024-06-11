package org.example.vdtvideocall.services;

import io.micrometer.common.util.StringUtils;
import org.example.vdtvideocall.model.CallInfo;
import org.example.vdtvideocall.model.Employee;
import org.example.vdtvideocall.payload.request.CallRequest;
import org.example.vdtvideocall.payload.response.CallEndedResponse;
import org.example.vdtvideocall.payload.response.CallResponse;
import org.example.vdtvideocall.repositories.CallRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
public class CallHandlerService {
    private final SimpMessageSendingOperations messagingTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final EmployeeService employeeService;
    private final CallRepository callRepository;

    @Value("${queue.name}")
    private String queueName;

    @Value("${topic-exchange.name}")
    private String routingKey;

    @Autowired
    public CallHandlerService(RabbitTemplate rabbitTemplate, EmployeeService employeeService, CallRepository callRepository, SimpMessageSendingOperations messagingTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.employeeService = employeeService;
        this.callRepository = callRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public CallResponse queueCall(CallRequest callRequest){
        // create call info object in the db
        CallInfo callInfo = new CallInfo();
        callInfo.setReceivedAt(Instant.now());
        callInfo.setQueued(true);
        callInfo.setRoomKey(callRequest.getCallerRoomKey());
        CallInfo savedCallInfo = callRepository.save(callInfo);

        rabbitTemplate.convertAndSend(routingKey,savedCallInfo.getCallId());

        return toCallResponse(savedCallInfo);
    }
    @Scheduled(cron = "* * * ? * *")
    public void handleNextQueuedCall() {
        Optional<Employee> employeeResponse = employeeService.getAvailableEmployee();
        employeeResponse.ifPresent(employee -> {
            String queuedCallId = rabbitTemplate.receiveAndConvert(queueName, ParameterizedTypeReference.forType(String.class));
            if(StringUtils.isBlank(queuedCallId)) return;
            //log.info(String.format("received id %s from queue..", queuedCallId));

            Optional<CallInfo> optionalQueuedCall = callRepository.findById(queuedCallId);
            optionalQueuedCall.ifPresent(callInfo -> {
                if(callInfo.getEndedAt() != null) return;
                employeeService.updateEmployeeStatus(employee.getId(), Employee.Status.ON_CALL);
                //log.info(String.format("queued call from %s (%s) ID = %s is being handled by %s %s", callInfo.getCallerName(), callInfo.getCallerId(), callInfo.getId(), employee.getFirstName(), employee.getLastName()));
                callInfo.setInProgress(true);
                callInfo.setEmployeeId(employee.getId());
                callInfo.setStartedAt(Instant.now());
                callInfo.setQueued(false);
                callRepository.save(callInfo);
                CallResponse callResponse = new CallResponse();
                callResponse.setId(callInfo.getCallId());
                callResponse.setEmployeeId(employee.getId());
                callResponse.setRoomKey(callInfo.getRoomKey());
                messagingTemplate.convertAndSend("/topic/" + employee.getId(), callResponse);
            });
        });
    }
    public CallEndedResponse endCall(String callInfoId) {
        CallInfo callInfo = callRepository.findByCallId(callInfoId).get();

        boolean wasInQueue = callInfo.isQueued();

        callInfo.setEndedAt(Instant.now());
        callInfo.setInProgress(false);
        callInfo.setQueued(false);

        // update call info
        callRepository.save(callInfo);

        // free up the call handler if call to end was not in queue
        if(!wasInQueue) {
            employeeService.updateEmployeeStatus(callInfo.getEmployeeId(), Employee.Status.AVAILABLE);
        }

        return toCallEndedResponse(callInfo);
    }
    private static CallResponse toCallResponse(CallInfo callInfo) {
        CallResponse callResponse = new CallResponse();
        callResponse.setId(callInfo.getCallId());
        return callResponse;
    }

    private CallEndedResponse toCallEndedResponse(CallInfo callInfo) {
        CallEndedResponse callEndedResponse = new CallEndedResponse();

        if(callInfo.getStartedAt() != null && callInfo.getEndedAt() != null) {
            callEndedResponse.setDuration(String.format("%d minutes", Duration.between(callInfo.getStartedAt(), callInfo.getEndedAt()).toMinutes()));
        }
        return callEndedResponse;
    }
}
