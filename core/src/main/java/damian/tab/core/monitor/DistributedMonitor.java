package damian.tab.core.monitor;

import damian.tab.core.monitor.algorithm.RicartAgrawalaExecutor;
import damian.tab.core.proto.SynchroMessage;
import damian.tab.core.thread.ClientListenerRunnable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DistributedMonitor implements RicartAgrawalaSynchronizer{
    private final ClientListenerRunnable clientListenerRunnable;
    private final RicartAgrawalaExecutor algorithmExecutor;
    private final String monitorId;

    @Override
    public void dLock() {
//        todo tworzenie requesta o sekcje krytyczna by zapamietac liczbe ACK
        algorithmExecutor.sendMessageForCriticalSection(clientListenerRunnable, SynchroMessage.MessageType.LOCK_ACK, monitorId);
//        todo tutaj jakis mutex
    }

    @Override
    public void dUnlock() {
//        todo tutaj rozeslac innym czekajacym w kolejce ACK
    }

    @Override
    public void dWait() {
//        todo tutaj jakis mutex + request na oczekiwanie notify
    }

    @Override
    public void dNotify() {
        algorithmExecutor.sendMessageForCriticalSection(clientListenerRunnable, SynchroMessage.MessageType.NOTIFY, monitorId);

    }

    @Override
    public void dNotifyAll() {
        algorithmExecutor.sendMessageForCriticalSection(clientListenerRunnable, SynchroMessage.MessageType.NOTIFY_ALL, monitorId);
    }
}
