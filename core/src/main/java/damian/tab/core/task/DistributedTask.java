package damian.tab.core.task;

import damian.tab.core.monitor.DistributedMonitor;
import damian.tab.core.monitor.algorithm.RicartAgrawalaExecutor;
import damian.tab.core.thread.ClientListenerRunnable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DistributedTask implements DistributedTaskInterface {

    private ClientListenerRunnable clientListenerRunnable;
    private RicartAgrawalaExecutor algorithmExecutor;

    @Override
    public void assignClientListener(ClientListenerRunnable clientListener) {
        this.clientListenerRunnable = clientListener;
    }

    @Override
    public void assignAlgorithmExecutor(RicartAgrawalaExecutor algorithmExecutor) {
        this.algorithmExecutor = algorithmExecutor;
    }

    @Override
    public DistributedMonitor createNewMonitor(String monitorId) {
        return new DistributedMonitor(clientListenerRunnable, algorithmExecutor, monitorId);
    }
}
