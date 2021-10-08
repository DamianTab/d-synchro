package damian.tab.core.monitor.algorithm;

import damian.tab.core.monitor.algorithm.model.LockRequest;
import damian.tab.core.monitor.algorithm.model.NotifyRequest;
import damian.tab.core.thread.model.ProcessData;
import org.springframework.stereotype.Service;

@Service
public class RequestShepherd {

    public LockRequest addNewLockRequest(ProcessData processData) {
        synchronized (processData) {
            LockRequest lockRequest = new LockRequest(processData);
            processData.getLockUnlockRequests().add(lockRequest);
            return lockRequest;
        }
    }

    public NotifyRequest addNewNotifyRequest(ProcessData processData) {
        synchronized (processData) {
            NotifyRequest notifyRequest = new NotifyRequest(processData);
            processData.getWaitNotifyRequests().add(notifyRequest);
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
        }
    }
}
