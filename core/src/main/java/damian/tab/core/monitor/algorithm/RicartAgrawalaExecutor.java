package damian.tab.core.monitor.algorithm;

import damian.tab.core.monitor.algorithm.model.LockRequest;
import damian.tab.core.monitor.algorithm.model.NotifyRequest;
import damian.tab.core.proto.SynchroMessage;
import damian.tab.core.thread.ClientListenerRunnable;
import damian.tab.core.thread.model.ProcessData;
import damian.tab.core.zmq.SocketProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
                switch (synchroMessage.getType()) {
                    case LOCK_REQ:
                        handleLockREQ(clientListenerRunnable, processData, monitorId, synchroMessage);
                        break;
                    case LOCK_ACK:
                        handleLockACK(processData, monitorId, synchroMessage);
                        break;
                    case NOTIFY:
                        handleNotify(clientListenerRunnable, processData, monitorId, synchroMessage);
                        break;
                    case NOTIFY_ALL:
                        handleNotifyALL(clientListenerRunnable, processData, monitorId, synchroMessage);
                        break;
                    case NOTIFY_RST:
                        handleNotifyRST(clientListenerRunnable, processData, monitorId, synchroMessage);
                        break;
                    case NOTIFY_REQ:
                        handleNotifyREQ(clientListenerRunnable, processData, monitorId, synchroMessage);
                        break;
                    case NOTIFY_ACK:
                        handleNotifyACK(clientListenerRunnable, processData, monitorId, synchroMessage);
                        break;
                }
            }
        }
    }

    public void sendMessageAboutCriticalSection(ClientListenerRunnable clientListenerRunnable, SynchroMessage.MessageType messageType, String monitorId) {
        ProcessData processData = clientListenerRunnable.getProcessData();
        SynchroMessage.Builder messageBuilder = createSynchroMessage(processData, messageType, monitorId);
        if (messageType == SynchroMessage.MessageType.NOTIFY) {
            int notifyId = processData.getNotifyIdGenerator().incrementAndGet();
            messageBuilder.setNotifyID(notifyId);
        }
        messageHandler.sendMessage(clientListenerRunnable.getPublisher(), processData, messageBuilder);
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

    public void informAboutFinishedNotifySession(ClientListenerRunnable clientListenerRunnable, String monitorId, NotifyRequest notifyRequest) {
//        Send RST about this notify session
        ProcessData processData = clientListenerRunnable.getProcessData();
        SynchroMessage.Builder messageBuilder = createSynchroMessage(processData, SynchroMessage.MessageType.NOTIFY_RST, monitorId);
        messageBuilder.setNotifyID(notifyRequest.getOngoingNotifySessions().get(0));
        messageHandler.sendMessage(clientListenerRunnable.getPublisher(), processData, messageBuilder);

//        Send ACK about others waiting notifySessions
        notifyRequest.getOngoingNotifySessions().forEach(notifySession -> {
            List<Integer> receivers = notifyRequest.getWaitingQueue().stream()
                    .filter(message -> message.getObjectID().equals(monitorId))
                    .filter(message -> message.getNotifyID() == notifySession)
                    .map(SynchroMessage::getProcessID)
                    .collect(Collectors.toList());

            if (!receivers.isEmpty()) {
                SynchroMessage.Builder builder = createSynchroMessage(processData, SynchroMessage.MessageType.NOTIFY_ACK, monitorId);
                builder.addAllReceiverProcessID(receivers);
                builder.setNotifyID(notifySession);
                messageHandler.sendMessage(clientListenerRunnable.getPublisher(), processData, builder);
            }
        });
    }

    private void handleLockREQ(ClientListenerRunnable clientListenerRunnable, ProcessData processData, String monitorId, SynchroMessage synchroMessage) {
        Optional<LockRequest> optionalLockRequest = processData.getLockUnlockRequests().stream()
                .filter(lockRequest -> lockRequest.getMonitorId().equals(monitorId))
                .findFirst();
        if (optionalLockRequest.isPresent()) {
            LockRequest lockRequest = optionalLockRequest.get();
            if (lockRequest.isInCriticalSection() || clockComparator.isClockLess(processData.getProcessId(), lockRequest.getClockTimestamp(), synchroMessage)) {
                lockRequest.getWaitingQueue().add(synchroMessage);
            } else {
                sendLockACK(clientListenerRunnable, processData, monitorId, synchroMessage);
            }
        } else {
            sendLockACK(clientListenerRunnable, processData, monitorId, synchroMessage);
        }
    }

    private void handleLockACK(ProcessData processData, String monitorId, SynchroMessage synchroMessage) {
        Optional<LockRequest> optionalLockRequest = processData.getLockUnlockRequests().stream()
                .filter(lockRequest -> lockRequest.getMonitorId().equals(monitorId))
                .findFirst();
        if (optionalLockRequest.isPresent()) {
            LockRequest lockRequest = optionalLockRequest.get();
//            If ackList needs ACK from this Process
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


    private void handleNotify(ClientListenerRunnable clientListenerRunnable, ProcessData processData, String monitorId, SynchroMessage synchroMessage) {
        Optional<NotifyRequest> optionalNotifyRequest = processData.getWaitNotifyRequests().stream()
                .filter(lockRequest -> lockRequest.getMonitorId().equals(monitorId))
                .findFirst();
        int notifyId = synchroMessage.getNotifyID();


        if (optionalNotifyRequest.isPresent()) {
            NotifyRequest notifyRequest = optionalNotifyRequest.get();
            List<Integer> ongoingSessions = notifyRequest.getOngoingNotifySessions();

            if (!ongoingSessions.contains(notifyId)) {
                ongoingSessions.add(notifyId);
                if (ongoingSessions.indexOf(notifyId) == 0) {
                    SynchroMessage.Builder messageBuilder = createSynchroMessage(processData, SynchroMessage.MessageType.NOTIFY_REQ, monitorId);
                    messageBuilder.setNotifyID(notifyId);
                    messageHandler.sendMessage(clientListenerRunnable.getPublisher(), processData, messageBuilder);
                }
            }
        } else {
            processData.getIgnoredNotifies().add(notifyId);
        }
    }

    private void handleNotifyALL(ClientListenerRunnable clientListenerRunnable, ProcessData processData, String monitorId, SynchroMessage synchroMessage) {
        processData.getWaitNotifyRequests().stream()
                .filter(notifyRequest -> notifyRequest.getMonitorId().equals(monitorId))
                .forEach(notifyRequest -> {
                    notifyRequest.getOngoingNotifySessions().forEach(notifySession -> processData.getIgnoredNotifies().add(notifySession));
                    notifyRequest.getOngoingNotifySessions().set(0, -1);
                    synchronized (notifyRequest) {
                        notifyRequest.notify();
                    }
                });
    }

    private void handleNotifyRST(ClientListenerRunnable clientListenerRunnable, ProcessData processData, String monitorId, SynchroMessage synchroMessage) {
        int invalidNotifySession = synchroMessage.getNotifyID();
        processData.getIgnoredNotifies().add(invalidNotifySession);

        processData.getWaitNotifyRequests().forEach(notifyRequest -> {
            List<Integer> ongoingSessions = notifyRequest.getOngoingNotifySessions();
            List<SynchroMessage> messagesToDelete = notifyRequest.getWaitingQueue().stream()
                    .filter(message -> message.getObjectID().equals(monitorId))
                    .filter(message -> message.getNotifyID() == invalidNotifySession)
                    .collect(Collectors.toList());
            notifyRequest.getWaitingQueue().removeAll(messagesToDelete);

            boolean currentSession = !ongoingSessions.isEmpty() && ongoingSessions.get(0) == invalidNotifySession;
            ongoingSessions.remove((Integer) invalidNotifySession);
            if (currentSession && !ongoingSessions.isEmpty()) {
                notifyRequest.getAckList().clear();
                notifyRequest.getAckList().addAll(Collections.nCopies(processData.getClock().size(), 0));
                notifyRequest.getAckList().set(processData.getProcessId(), 1);

                int newSessionId = ongoingSessions.get(0);
                notifyRequest.getWaitingQueue().stream()
                        .filter(message -> message.getNotifyID() == newSessionId)
                        .forEach(message -> handleNotifyREQ(clientListenerRunnable, processData, monitorId, message));
            }
        });
    }

    private void handleNotifyREQ(ClientListenerRunnable clientListenerRunnable, ProcessData processData, String monitorId, SynchroMessage synchroMessage) {
        int notifyId = synchroMessage.getNotifyID();
        Optional<NotifyRequest> optionalNotifyRequest = processData.getWaitNotifyRequests().stream()
                .filter(notifyRequest -> !processData.getIgnoredNotifies().contains(notifyId))
                .filter(notifyRequest -> notifyRequest.getMonitorId().equals(monitorId))
                .filter(notifyRequest -> notifyRequest.getOngoingNotifySessions().contains(notifyId))
                .findFirst();

        if (optionalNotifyRequest.isPresent()) {
            NotifyRequest notifyRequest = optionalNotifyRequest.get();
            List<Integer> ongoingSessions = notifyRequest.getOngoingNotifySessions();
            if (ongoingSessions.indexOf(synchroMessage.getNotifyID()) > 0 || (ongoingSessions.indexOf(synchroMessage.getNotifyID()) == 0 && clockComparator.isClockLess(processData.getProcessId(), notifyRequest.getClockTimestamp(), synchroMessage))) {
                notifyRequest.getWaitingQueue().add(synchroMessage);
            } else {
                sendNotifyACK(clientListenerRunnable, processData, monitorId, synchroMessage);
            }
        } else {
            sendNotifyACK(clientListenerRunnable, processData, monitorId, synchroMessage);
        }
    }

    private void handleNotifyACK(ClientListenerRunnable clientListenerRunnable, ProcessData processData, String monitorId, SynchroMessage synchroMessage) {
        int notifyId = synchroMessage.getNotifyID();
        Optional<NotifyRequest> optionalNotifyRequest = processData.getWaitNotifyRequests().stream()
                .filter(notifyRequest -> !processData.getIgnoredNotifies().contains(notifyId))
                .filter(notifyRequest -> notifyRequest.getMonitorId().equals(monitorId))
                .filter(notifyRequest -> !notifyRequest.getOngoingNotifySessions().isEmpty())
                .filter(notifyRequest -> notifyRequest.getOngoingNotifySessions().get(0) == notifyId)
                .findFirst();

        if (optionalNotifyRequest.isPresent()) {
            NotifyRequest notifyRequest = optionalNotifyRequest.get();
//            If ackList needs ACK from this Process
            if (notifyRequest.getAckList().size() > synchroMessage.getProcessID()) {
                notifyRequest.getAckList().set(synchroMessage.getProcessID(), 1);
                if (notifyRequest.isPossibleToAcquireCriticalSection()) {
                    synchronized (notifyRequest) {
                        notifyRequest.notify();
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

    private void sendNotifyACK(ClientListenerRunnable clientListenerRunnable, ProcessData processData, String monitorId, SynchroMessage synchroMessage) {
        SynchroMessage.Builder messageBuilder = createSynchroMessage(processData, SynchroMessage.MessageType.NOTIFY_ACK, monitorId);
        messageBuilder.addReceiverProcessID(synchroMessage.getProcessID());
        messageBuilder.setNotifyID(synchroMessage.getNotifyID());
        messageHandler.sendMessage(clientListenerRunnable.getPublisher(), processData, messageBuilder);
    }

    private SynchroMessage.Builder createSynchroMessage(ProcessData processData, SynchroMessage.MessageType messageType, String monitorId) {
        return SynchroMessage.newBuilder()
                .setProcessID(processData.getProcessId())
                .setType(messageType)
                .setObjectID(monitorId);
    }


}
