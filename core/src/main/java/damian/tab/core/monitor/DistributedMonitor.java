package damian.tab.core.monitor;

import damian.tab.core.monitor.algorithm.RequestShepherd;
import damian.tab.core.monitor.algorithm.RicartAgrawalaExecutor;
import damian.tab.core.monitor.algorithm.model.LockRequest;
import damian.tab.core.monitor.algorithm.model.NotifyRequest;
import damian.tab.core.proto.SynchroMessage;
import damian.tab.core.thread.ClientListenerRunnable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DistributedMonitor implements RicartAgrawalaSynchronizer {
    private final String monitorId;
    private final ClientListenerRunnable clientListenerRunnable;
    private final RicartAgrawalaExecutor algorithmExecutor;
    private final RequestShepherd requestShepherd;

    private LockRequest lockRequest;

    @Override
    public void dLock() {
        log.info("----------------------- Monitor {} - D_LOCK", monitorId);

        synchronized (clientListenerRunnable.getProcessData()){
            algorithmExecutor.sendMessageAboutCriticalSection(clientListenerRunnable, SynchroMessage.MessageType.LOCK_REQ, monitorId);
            lockRequest = requestShepherd.addNewLockRequest(monitorId, clientListenerRunnable.getProcessData());
        }

        synchronized (lockRequest){
            if (!lockRequest.isInCriticalSection()){
                try {
                    lockRequest.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        log.info("----------------------- Monitor {} - LOCK_UNLOCK CRITICAL SECTION", monitorId);
    }

    @Override
    public void dUnlock() {
        log.info("----------------------- Monitor {} - D_UNLOCK", monitorId);
        requestShepherd.removeRequest(clientListenerRunnable.getProcessData(), lockRequest);
        algorithmExecutor.sendLockACKToWaitingProcesses(clientListenerRunnable, monitorId, lockRequest);
        lockRequest = null;
    }

    @Override
    public void dWait() {
        log.info("----------------------- Monitor {} - WAIT", monitorId);

        //todo tutaj tez trzeba synchronizowac zegar w request by ybl taki sam jak przy wysylce
        NotifyRequest notifyRequest = requestShepherd.addNewNotifyRequest(monitorId, clientListenerRunnable.getProcessData());
        if (!notifyRequest.isInCriticalSection()){
            try {
                notifyRequest.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("----------------------- Monitor {} - Received notify", monitorId);
        //        todo tutaj rozeslac innym czekajacym ze koniec tury
    }

    @Override
    public void dNotify() {
        log.info("----------------------- Monitor {} - NOTIFY", monitorId);
        algorithmExecutor.sendMessageAboutCriticalSection(clientListenerRunnable, SynchroMessage.MessageType.NOTIFY, monitorId);
    }

    @Override
    public void dNotifyAll() {
        log.info("----------------------- Monitor {} - NOTIFY_ALL", monitorId);
        algorithmExecutor.sendMessageAboutCriticalSection(clientListenerRunnable, SynchroMessage.MessageType.NOTIFY_ALL, monitorId);
    }
}
