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

    public void sendMessage(SocketProxy socketProxy, ProcessData processData, SynchroMessage message) {
        synchronized (processData) {
            clockSynchronizer.incrementClock(processData);
            proxyHandler.send(socketProxy, message);
            log.info("Sended message {}: {}", message.getType(), message);
        }
    }

    public SynchroMessage receiveMessage(SocketProxy socketProxy, ProcessData processData) {
        synchronized (processData) {
            SynchroMessage synchroMessage = (SynchroMessage) proxyHandler.receive(socketProxy);
            clockSynchronizer.incrementClock(processData);
            clockSynchronizer.synchronizeClock(processData, synchroMessage.getClockList());
            log.info("Received synchro message {}: {}", synchroMessage.getType(), synchroMessage);
            return synchroMessage;
        }
    }
}
