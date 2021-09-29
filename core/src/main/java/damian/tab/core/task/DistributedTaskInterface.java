package damian.tab.core.task;

import damian.tab.core.monitor.DistributedMonitor;
import damian.tab.core.monitor.algorithm.RicartAgrawalaExecutor;
import damian.tab.core.thread.ClientListenerRunnable;

public interface DistributedTaskInterface extends Runnable {
    void assignClientListener(ClientListenerRunnable clientListener);

    void assignAlgorithmExecutor(RicartAgrawalaExecutor algorithmExecutor);

    DistributedMonitor createNewMonitor(String monitorId);
}
