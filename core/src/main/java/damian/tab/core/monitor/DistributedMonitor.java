package damian.tab.core.monitor;

import damian.tab.core.monitor.algorithm.RicartAgrawalaExecutor;
import damian.tab.core.thread.ClientListenerRunnable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DistributedMonitor implements RicartAgrawalaSynchronizer{
    private final ClientListenerRunnable clientListenerRunnable;
    private final RicartAgrawalaExecutor algorithmExecutor;
    private final String objectId;

    @Override
    public void dLock() {

    }

    @Override
    public void dUnlock() {

    }

    @Override
    public void dWait() {

    }

    @Override
    public void dNotify() {

    }

    @Override
    public void dNotifyAll() {

    }
}
