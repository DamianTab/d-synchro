package damian.tab.core.task;

import damian.tab.core.monitor.DistributedMonitor;
import damian.tab.core.thread.ClientListenerRunnable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DistributedTask implements Runnable {

    private ClientListenerRunnable clientListenerRunnable;

    public void assignClientListener(ClientListenerRunnable clientListener) {
        this.clientListenerRunnable = clientListener;
    }

    public DistributedMonitor createNewMonitor(String monitorId) {
        return new DistributedMonitor(clientListenerRunnable, monitorId);
    }
}
