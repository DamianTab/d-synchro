package damian.tab.core.monitor.algorithm;

import damian.tab.core.proto.SynchroMessage;
import damian.tab.core.thread.model.ProcessData;
import damian.tab.core.zmq.SocketProxy;
import damian.tab.core.zmq.SocketProxyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHandler {
    private final SocketProxyHandler proxyHandler;
    private final ClockSynchronizer clockSynchronizer;

    public void sendMessage(SocketProxy socketProxy, ProcessData processData, SynchroMessage.Builder builder) {
        synchronized (processData) {
            clockSynchronizer.incrementClock(processData);
            SynchroMessage message = builder
                    .addAllClock(processData.getClock())
                    .build();
            proxyHandler.send(socketProxy, message);
            log.info("\n -------- Send message: {}", synchroMessageAsString(message));
        }
    }

    public SynchroMessage receiveMessage(SocketProxy socketProxy, ProcessData processData) {
        synchronized (processData) {
            SynchroMessage message = (SynchroMessage) proxyHandler.receive(socketProxy);
            if (message.getReceiverProcessIDList().isEmpty() || message.getReceiverProcessIDList().contains(processData.getProcessId())){
                log.info("\n -------- Received message: {}", synchroMessageAsString(message));
                clockSynchronizer.incrementClock(processData);
                clockSynchronizer.synchronizeClock(processData, message.getClockList());
                return message;
            }
            return null;
        }
    }

    private String synchroMessageAsString(SynchroMessage synchroMessage) {
        return "messageType: " + synchroMessage.getType() + "  | " +
                "clock: " + synchroMessage.getClockList() + "  | " +
                "senderProcessId: " + synchroMessage.getProcessID() + "  | " +
                "receiverProcessId: " + synchroMessage.getReceiverProcessIDList() + "  | " +
                "monitorId: " + synchroMessage.getObjectID() + "  | " +
                "notifyId: " + synchroMessage.getNotifyID();
    }
}
