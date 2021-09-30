package damian.tab.core.monitor.algorithm;

import damian.tab.core.proto.SynchroMessage;
import damian.tab.core.thread.ClientListenerRunnable;
import damian.tab.core.thread.model.ProcessData;
import damian.tab.core.zmq.SocketProxyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RicartAgrawalaExecutor {
    private final SocketProxyHandler proxyHandler;
    private final ClockSynchronizer clockSynchronizer;

    public void expandClock(ClientListenerRunnable clientListenerRunnable) {
        clockSynchronizer.expandClock(clientListenerRunnable.getProcessData());
        log.info("Expand clock to: {}", clientListenerRunnable.getProcessData().getClock());

    }

    public void handleSynchroMessage(ClientListenerRunnable clientListenerRunnable, SynchroMessage message) {

    }

    public void sendMessageForCriticalSection(ClientListenerRunnable clientListenerRunnable, SynchroMessage.MessageType messageType, String monitorId) {
        ProcessData processData = clientListenerRunnable.getProcessData();

        SynchroMessage.Builder messageBuilder = createSynchroMessage(processData, messageType, monitorId);
        if (messageType == SynchroMessage.MessageType.NOTIFY || messageType == SynchroMessage.MessageType.NOTIFY_ALL) {
            messageBuilder.setNotifyID(processData.getNotifyIdGenerator().incrementAndGet());
        }
        proxyHandler.send(clientListenerRunnable.getPublisher(), messageBuilder.build());
    }

    private SynchroMessage.Builder createSynchroMessage(ProcessData processData, SynchroMessage.MessageType messageType, String monitorId, List<Integer> receivers, int notifyID) {
        return createSynchroMessage(processData, messageType, monitorId)
                .addAllReceiverProcessID(receivers)
                .setNotifyID(notifyID);
    }

    private SynchroMessage.Builder createSynchroMessage(ProcessData processData, SynchroMessage.MessageType messageType, String monitorId) {
        return SynchroMessage.newBuilder()
                .setProcessID(processData.getProcessId())
                .addAllClock(processData.getClock())
                .setType(messageType)
                .setObjectID(monitorId);
    }

}
