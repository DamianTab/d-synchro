package damian.tab.core.monitor.algorithm;

import damian.tab.core.monitor.algorithm.model.LockRequest;
import damian.tab.core.proto.SynchroMessage;
import damian.tab.core.thread.ClientListenerRunnable;
import damian.tab.core.thread.model.ProcessData;
import damian.tab.core.zmq.SocketProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RicartAgrawalaExecutor {
    private final MessageHandler messageHandler;

    public void handleSynchroMessage(ClientListenerRunnable clientListenerRunnable, SocketProxy socketProxy) {
        ProcessData processData = clientListenerRunnable.getProcessData();
        SynchroMessage synchroMessage = messageHandler.receiveMessage(socketProxy, processData);
        log.info("Received synchro message: {}", synchroMessage);
        // todo dorobic obsluge lock
        // todo dorobic obsluge notify
    }

    public void sendLockACKToWaitingProcesses(ClientListenerRunnable clientListenerRunnable, String monitorId, LockRequest lockRequest) {
        ProcessData processData = clientListenerRunnable.getProcessData();
        SynchroMessage.Builder messageBuilder = createSynchroMessage(processData, SynchroMessage.MessageType.LOCK_ACK, monitorId);
        messageBuilder.addAllReceiverProcessID(lockRequest.getWaitingQueue());
        messageHandler.sendMessage(clientListenerRunnable.getPublisher(), processData, messageBuilder.build());
    }

    public void sendMessageAboutCriticalSection(ClientListenerRunnable clientListenerRunnable, SynchroMessage.MessageType messageType, String monitorId) {
        ProcessData processData = clientListenerRunnable.getProcessData();
        SynchroMessage.Builder messageBuilder = createSynchroMessage(processData, messageType, monitorId);
        if (messageType == SynchroMessage.MessageType.NOTIFY || messageType == SynchroMessage.MessageType.NOTIFY_ALL) {
            int notifyId = processData.getNotifyIdGenerator().incrementAndGet();
            messageBuilder.setNotifyID(notifyId);
        }
        messageHandler.sendMessage(clientListenerRunnable.getPublisher(), processData, messageBuilder.build());
    }

    private SynchroMessage.Builder createSynchroMessage(ProcessData processData, SynchroMessage.MessageType messageType, String monitorId) {
        return SynchroMessage.newBuilder()
                .setProcessID(processData.getProcessId())
                .addAllClock(processData.synchronizedGetClock())
                .setType(messageType)
                .setObjectID(monitorId);
    }

}
