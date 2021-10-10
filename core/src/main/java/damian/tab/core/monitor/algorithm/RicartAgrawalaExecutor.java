package damian.tab.core.monitor.algorithm;

import damian.tab.core.monitor.algorithm.model.LockRequest;
import damian.tab.core.proto.SynchroMessage;
import damian.tab.core.thread.ClientListenerRunnable;
import damian.tab.core.thread.model.ProcessData;
import damian.tab.core.zmq.SocketProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RicartAgrawalaExecutor {
    private final MessageHandler messageHandler;
    private final ClockComparator clockComparator;

    public void handleSynchroMessage(ClientListenerRunnable clientListenerRunnable, SocketProxy socketProxy) {
        ProcessData processData = clientListenerRunnable.getProcessData();
        synchronized (processData) {
            SynchroMessage synchroMessage = messageHandler.receiveMessage(socketProxy, processData);

            if (synchroMessage != null) {
                String monitorId = synchroMessage.getObjectID();
                // todo dorobic obsluge notify
                switch (synchroMessage.getType()) {
                    case LOCK_REQ:
                        handleLOCK_REQ(clientListenerRunnable, processData, monitorId, synchroMessage);
                        break;
                    case LOCK_ACK:
                        handleLOCK_ACK(processData, monitorId, synchroMessage);
                        break;
                }
            }
        }
    }

    public void sendLockACKToWaitingProcesses(ClientListenerRunnable clientListenerRunnable, String monitorId, LockRequest lockRequest) {
        List<Integer> receivers = lockRequest.getWaitingQueue().stream()
                .map(SynchroMessage::getProcessID)
                .collect(Collectors.toList());
        if (!receivers.isEmpty()) {
            ProcessData processData = clientListenerRunnable.getProcessData();
            SynchroMessage.Builder messageBuilder = createSynchroMessage(processData, SynchroMessage.MessageType.LOCK_ACK, monitorId);
            messageBuilder.addAllReceiverProcessID(receivers);
            messageHandler.sendMessage(clientListenerRunnable.getPublisher(), processData, messageBuilder);
        }
    }

    public void sendMessageAboutCriticalSection(ClientListenerRunnable clientListenerRunnable, SynchroMessage.MessageType messageType, String monitorId) {
        ProcessData processData = clientListenerRunnable.getProcessData();
        SynchroMessage.Builder messageBuilder = createSynchroMessage(processData, messageType, monitorId);
        if (messageType == SynchroMessage.MessageType.NOTIFY || messageType == SynchroMessage.MessageType.NOTIFY_ALL) {
            int notifyId = processData.getNotifyIdGenerator().incrementAndGet();
            messageBuilder.setNotifyID(notifyId);
        }
        messageHandler.sendMessage(clientListenerRunnable.getPublisher(), processData, messageBuilder);
    }

    private void handleLOCK_REQ(ClientListenerRunnable clientListenerRunnable, ProcessData processData, String monitorId, SynchroMessage synchroMessage) {
        Optional<LockRequest> optionalLockRequest = processData.getLockUnlockRequests().stream()
                .filter(lockRequest -> lockRequest.getMonitorId().equals(monitorId))
                .findFirst();
        if (optionalLockRequest.isPresent()) {
            LockRequest lockRequest = optionalLockRequest.get();
            if (lockRequest.isInCriticalSection() || clockComparator.isInternalClockLess(processData, synchroMessage)) {
                lockRequest.getWaitingQueue().add(synchroMessage);
            } else {
                sendLockACK(clientListenerRunnable, processData, monitorId, synchroMessage);
            }
        } else {
            sendLockACK(clientListenerRunnable, processData, monitorId, synchroMessage);
        }
    }

    private void handleLOCK_ACK(ProcessData processData, String monitorId, SynchroMessage synchroMessage) {
        Optional<LockRequest> optionalLockRequest = processData.getLockUnlockRequests().stream()
                .filter(lockRequest -> lockRequest.getMonitorId().equals(monitorId))
                .findFirst();
        if (optionalLockRequest.isPresent()) {
            LockRequest lockRequest = optionalLockRequest.get();
//            If ackList doesn't need ACK from this Process then abort
            if (lockRequest.getAckList().size() > synchroMessage.getProcessID()) {
                lockRequest.getAckList().set(synchroMessage.getProcessID(), 1);
                if (lockRequest.isPossibleToAcquireCriticalSection()) {
                    lockRequest.setInCriticalSection(true);
                    synchronized (lockRequest) {
                        lockRequest.notify();
                    }
                }
            }
        }
    }

    private void sendLockACK(ClientListenerRunnable clientListenerRunnable, ProcessData processData, String monitorId, SynchroMessage synchroMessage) {
        SynchroMessage.Builder messageBuilder = createSynchroMessage(processData, SynchroMessage.MessageType.LOCK_ACK, monitorId);
        messageBuilder.addReceiverProcessID(synchroMessage.getProcessID());
        messageHandler.sendMessage(clientListenerRunnable.getPublisher(), processData, messageBuilder);
    }

    private SynchroMessage.Builder createSynchroMessage(ProcessData processData, SynchroMessage.MessageType messageType, String monitorId) {
        return SynchroMessage.newBuilder()
                .setProcessID(processData.getProcessId())
                .setType(messageType)
                .setObjectID(monitorId);
    }


}
