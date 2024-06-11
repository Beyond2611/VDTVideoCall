package org.example.vdtvideocall.repositories;

import org.example.vdtvideocall.model.CallInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CallRepository extends MongoRepository<CallInfo, String> {
    Optional<CallInfo> findByCallId(String callId);
    Page<CallInfo> findCallInfoByisQueuedTrueOrderByReceivedAt(Pageable pageable);
}
