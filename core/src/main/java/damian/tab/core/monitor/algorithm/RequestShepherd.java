package damian.tab.core.monitor.algorithm;

import damian.tab.core.monitor.algorithm.model.LockRequest;
import damian.tab.core.monitor.algorithm.model.NotifyRequest;
import damian.tab.core.thread.model.ProcessData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestShepherd {
    private final ClockSynchronizer clockSynchronizer;

    public LockRequest addNewLockRequest(String monitorId, ProcessData processData) {
        synchronized (processData) {
            LockRequest lockRequest = new LockRequest(monitorId, processData);
            processData.getLockUnlockRequests().add(lockRequest);
            clockSynchronizer.incrementClock(processData);
            acquireCriticalSectionIfNoOtherProcessAlive(lockRequest);
            return lockRequest;
        }
    }

    public NotifyRequest addNewNotifyRequest(String monitorId, ProcessData processData) {
        synchronized (processData) {
            NotifyRequest notifyRequest = new NotifyRequest(monitorId, processData);
            processData.getWaitNotifyRequests().add(notifyRequest);
            clockSynchronizer.incrementClock(processData);
            acquireCriticalSectionIfNoOtherProcessAlive(notifyRequest);
            return notifyRequest;
        }
    }

    public void removeRequest(ProcessData processData, LockRequest request){
        synchronized (processData){
            if (request instanceof NotifyRequest){
                processData.getWaitNotifyRequests().remove(request);
            }else{
                processData.getLockUnlockRequests().remove(request);
            }
            request.setInCriticalSection(false);
            clockSynchronizer.incrementClock(processData);
        }
    }

    private void acquireCriticalSectionIfNoOtherProcessAlive(LockRequest request){
        if (request.isPossibleToAcquireCriticalSection()){
            request.setInCriticalSection(true);
        }
    }
}
