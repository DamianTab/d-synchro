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
        lockRequest = requestShepherd.addNewLockRequest(monitorId, clientListenerRunnable.getProcessData());
        algorithmExecutor.sendMessageAboutCriticalSection(clientListenerRunnable, SynchroMessage.MessageType.LOCK_ACK, monitorId);
        log.info("Monitor {} - dLock", monitorId);
        if (!lockRequest.isInCriticalSection()){
            try {
                lockRequest.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("Monitor {} - in critical session", monitorId);
    }

    @Override
    public void dUnlock() {
        requestShepherd.removeRequest(clientListenerRunnable.getProcessData(), lockRequest);
        log.info("Monitor {} - dUnlock", monitorId);
        algorithmExecutor.sendLockACKToWaitingProcesses(clientListenerRunnable, monitorId, lockRequest);
        lockRequest = null;
    }

    @Override
    public void dWait() {
        NotifyRequest notifyRequest = requestShepherd.addNewNotifyRequest(monitorId, clientListenerRunnable.getProcessData());
        if (!notifyRequest.isInCriticalSection()){
            try {
                notifyRequest.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //        todo tutaj rozeslac innym czekajacym ze koniec tury
    }

    @Override
    public void dNotify() {
        algorithmExecutor.sendMessageAboutCriticalSection(clientListenerRunnable, SynchroMessage.MessageType.NOTIFY, monitorId);
    }

    @Override
    public void dNotifyAll() {
        algorithmExecutor.sendMessageAboutCriticalSection(clientListenerRunnable, SynchroMessage.MessageType.NOTIFY_ALL, monitorId);
    }
}
