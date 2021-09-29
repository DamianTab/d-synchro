package damian.tab.core.monitor.algorithm;

import damian.tab.core.thread.ClientListenerRunnable;
import damian.tab.core.zmq.SocketProxyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RicartAgrawalaExecutor {
    private final SocketProxyHandler proxyHandler;
    private final ClockSynchronizer clockSynchronizer;
    private final NotifyIdGenerator notifyIdGenerator;

    public void expandClock(ClientListenerRunnable clientListenerRunnable){
        clockSynchronizer.expandClock(clientListenerRunnable.getProcessData());
        log.info("Expand clock to: {}", clientListenerRunnable.getProcessData().getClock());

    }

}
