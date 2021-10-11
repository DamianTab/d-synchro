package damian.tab.core.monitor.algorithm;

import damian.tab.core.monitor.algorithm.model.CriticalSectionRequest;
import damian.tab.core.monitor.algorithm.model.LockRequest;
import damian.tab.core.monitor.algorithm.model.NotifyRequest;
import damian.tab.core.thread.model.ProcessData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestShepherd {
    private final ClockSynchronizer clockSynchronizer;

    public LockRequest addNewLockRequest(String monitorId, ProcessData processData) {
        synchronized (processData) {
            LockRequest lockRequest = new LockRequest(monitorId, processData);
            processData.getLockUnlockRequests().add(lockRequest);
            if (lockRequest.isPossibleToAcquireCriticalSection()){
                lockRequest.setInCriticalSection(true);
            }
            log.info("Created Lock Request {}", lockRequest);
            clockSynchronizer.incrementClock(processData);
            return lockRequest;
        }
    }

    public NotifyRequest addNewNotifyRequest(String monitorId, ProcessData processData) {
        synchronized (processData) {
            NotifyRequest notifyRequest = new NotifyRequest(monitorId, processData);
            processData.getWaitNotifyRequests().add(notifyRequest);
            log.info("Created Notify Request {}", notifyRequest);
            clockSynchronizer.incrementClock(processData);
            return notifyRequest;
        }
    }

    public void removeRequest(ProcessData processData, CriticalSectionRequest request){
        synchronized (processData){
            if (request instanceof NotifyRequest){
                processData.getWaitNotifyRequests().remove(request);
            }else{
                ((LockRequest)request).setInCriticalSection(false);
                processData.getLockUnlockRequests().remove(request);
            }
            clockSynchronizer.incrementClock(processData);
        }
    }
}
